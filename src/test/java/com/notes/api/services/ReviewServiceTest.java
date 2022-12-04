package com.notes.api.services;

import com.notes.api.TestUtils;
import com.notes.api.dto.FlashcardReviewDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.note.FlashcardBlock;
import com.notes.api.entities.note.Note;
import com.notes.api.entities.review.FlashcardReview;
import com.notes.api.repositories.NoteRepository;
import com.notes.api.repositories.ReviewRepository;
import com.notes.api.repositories.UserRepository;
import com.notes.api.responses.FlashcardInfo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewServiceTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Mock
    UserService userService;

    @Mock
    MailService mailService;

    @Autowired
    UserRepository userRepository;

    ReviewService reviewService;

    @Autowired
    NoteRepository noteRepository;

    @BeforeEach
    public void setup() {
        reviewService = new ReviewService(reviewRepository, userService, mailService);

        Note note = TestUtils.createNote(1, "test note", new Date());
        User user = TestUtils.getMockUser();

        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 2, 4));
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 1, 1));
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 2));
        FlashcardBlock flashcardBlock = TestUtils.createFlashcardBlock(3, 3);
        flashcardBlock.getFlashcards().add(TestUtils.createFlashcard(0, "test question", "test answer"));
        note.getFlashcardBlocks().add(flashcardBlock);

        userRepository.save(user);

        note.getFlashcardBlocks().forEach(fb -> {
            fb.setNote(note);
            fb.getFlashcards().forEach(f -> {
                FlashcardReview reviewSchedule = new FlashcardReview();
                reviewSchedule.setFlashcard(f);
//                f.setUser(userService.getSignedOnUser());
                f.setReview(reviewSchedule);
                f.setFlashcardBlock(fb);
                f.setNote(note);
            });
        });

        note.getRichTextBlocks().forEach( rtb -> rtb.setNote(note));
        note.getCodeBlocks().forEach( cb -> cb.setNote(note));

        noteRepository.save(note);
    }

    @Test
    public void givenSuccesfulReview_BucketIncrementedAndNextReviewSetCorrectly() {

        FlashcardReviewDTO newReviewResult = new FlashcardReviewDTO();
        newReviewResult.setRemembered(true);
        Note savedNote = noteRepository.findById(1);
        newReviewResult.setFlashcardId(savedNote.getFlashcardBlocks().get(0).getFlashcards().get(0).getId());

        reviewService.saveReviewResult(newReviewResult);

        List<FlashcardReview> reviews = reviewRepository.findAll();

        FlashcardReview review = reviews.get(0);

        Assertions.assertEquals(reviews.size(), 1);

        Assertions.assertEquals(review.getBucketType(), BucketType.Two);

        Duration nextReviewInterval = Duration.between(review.getLastReviewed(), review.getNextReview());

        Assertions.assertEquals(BucketType.Two.getInterval(), nextReviewInterval.toDays());
        Assertions.assertEquals(review.getTimesReviewed(), 1);
        Assertions.assertEquals(review.getTimesRemembered(), 1);
    }

    @Test
    public void given2SuccesfulReview1Unsuccesful_BucketIncrementedAndFinallyReset() {

        FlashcardReviewDTO newReviewResult = new FlashcardReviewDTO();
        newReviewResult.setRemembered(true);
        Note savedNote = noteRepository.findById(1);
        Long id = savedNote.getFlashcardBlocks().get(0).getFlashcards().get(0).getId();
        newReviewResult.setFlashcardId(id);
        // Bucket moves up to 2
        reviewService.saveReviewResult(newReviewResult);

        // Bucket moves up to 3
        newReviewResult.setRemembered(true);
        reviewService.saveReviewResult(newReviewResult);
        Assertions.assertEquals(reviewRepository.findByflashcard_id(id).getBucketType(), BucketType.Three);

        // Bucket moves back down to 1
        newReviewResult.setRemembered(false);
        reviewService.saveReviewResult(newReviewResult);

        List<FlashcardReview> reviews = reviewRepository.findAll();

        FlashcardReview review = reviews.get(0);

        Assertions.assertEquals(reviews.size(), 1);

        Assertions.assertEquals(review.getBucketType(), BucketType.One);

        System.out.println("Next review" + review.getNextReview());
        Duration nextReviewInterval = Duration.between(review.getLastReviewed(), review.getNextReview());

        Assertions.assertEquals(BucketType.One.getInterval(), nextReviewInterval.toDays());
        Assertions.assertEquals(review.getTimesReviewed(), 3);
        Assertions.assertEquals(review.getTimesRemembered(), 2);
    }

}
