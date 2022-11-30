package com.notes.api.repositories;

import com.notes.api.TestUtils;
import com.notes.api.dto.NoteDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.note.*;
import com.notes.api.entities.review.FlashcardReview;
import com.notes.api.responses.FlashcardInfo;
import com.notes.api.services.BucketType;
import com.notes.api.services.NoteService;
import com.notes.api.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RepositoryTest {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlashcardRepository flashcardRepository;

    @Mock
    UserService userService;

    @BeforeEach
    public void saveNote() {
        Note note = TestUtils.createNote(0, "test note", new Date());
        User user = TestUtils.getMockUser();
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 4));
        note.getCodeBlocks().add(TestUtils.createCodeBlock("abc", 0));
        note.setUser(user);
        FlashcardBlock flashcardBlock = TestUtils.createFlashcardBlock(0, 3);
        flashcardBlock.getFlashcards().add(TestUtils.createFlashcard(0, "test question", "test answer"));
        note.getFlashcardBlocks().add(flashcardBlock);

        userRepository.save(user);

        note.getFlashcardBlocks().forEach(fb -> {
            fb.setNote(note);
            fb.getFlashcards().forEach(f -> {
                FlashcardReview reviewSchedule = new FlashcardReview();
                reviewSchedule.setFlashcard(f);
//                f.setUser(user);
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
    public void givenNote_whenSaved_thenSameNoteReturned() {
        Note noteFromDb = noteRepository.findById(1);

        Assertions.assertNotNull(noteFromDb);
        Assertions.assertEquals(1, noteFromDb.getId() );

        Assertions.assertEquals(1, noteFromDb.getRichTextBlocks().size());
        RichTextBlock richTextBlockFromDb = noteFromDb.getRichTextBlocks().get(0);
        Assertions.assertEquals(1, richTextBlockFromDb.getNote().getId());
        Assertions.assertEquals("abc", richTextBlockFromDb.getData());

        Assertions.assertEquals(1, noteFromDb.getCodeBlocks().size());
        CodeBlock codeBlockFromDb = noteFromDb.getCodeBlocks().get(0);
        Assertions.assertEquals(1, codeBlockFromDb.getNote().getId());
        Assertions.assertEquals("abc", codeBlockFromDb.getData());

        FlashcardBlock flashcardBlockFromDb = noteFromDb.getFlashcardBlocks().get(0);
        Assertions.assertEquals(1, flashcardBlockFromDb.getNote().getId());
        Assertions.assertEquals(1, flashcardBlockFromDb.getFlashcards().size());

        Flashcard flashcardFromDb = flashcardBlockFromDb.getFlashcards().get(0);
        Assertions.assertEquals(1, flashcardFromDb.getNote().getId());
        Assertions.assertEquals(1, flashcardFromDb.getFlashcardBlock().getNote().getId());
        Assertions.assertEquals("test question", flashcardFromDb.getQuestion());
        Assertions.assertEquals("test answer", flashcardFromDb.getAnswer());
    }

//    @Test
//    @Transactional
//    public void givenSavedNote_whenDeleteNoteById_thenNoteCannotBeFoundInDb() {
//        noteRepository.deleteById(1);
//        Assertions.assertNull(noteRepository.findById(1));
//    }

    @Test
    public void givenNoteDTO_whenNewDTOReceived_thenOldCellsDeleted() {
        Note updatedNote = TestUtils.createNote(1, "test note", new Date());
        updatedNote.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 4));
        updatedNote.getCodeBlocks().add(TestUtils.createCodeBlock("abc", 0));

        updatedNote.getRichTextBlocks().forEach( rtb -> rtb.setNote(updatedNote));
        updatedNote.getCodeBlocks().forEach( cb -> cb.setNote(updatedNote));

        noteRepository.save(updatedNote);

        Note noteFromDb = noteRepository.findById(updatedNote.getId());
        List<RichTextBlock> richTextBlocksFromDb = noteFromDb.getRichTextBlocks();
        List<CodeBlock> codeBlocksFromDb = noteFromDb.getCodeBlocks();
        List<FlashcardBlock> flashcardBlocksFromDb = noteFromDb.getFlashcardBlocks();

        int numberOfBlocks = richTextBlocksFromDb.size() + flashcardBlocksFromDb.size() + codeBlocksFromDb.size();

        Assertions.assertNotNull(noteFromDb);
        Assertions.assertEquals(2, numberOfBlocks);
        Assertions.assertEquals(0, flashcardBlocksFromDb.size());
    }

    @Test
    public void saveNote_thenFetchUsingFlashcardIdFindsRightSchedule() {
        Note note = noteRepository.findById(1L);
        Assertions.assertNotNull(note);
        List<FlashcardBlock> flashcardBlocks = note.getFlashcardBlocks();
        flashcardBlocks.forEach(fb -> {
            fb.getFlashcards().forEach(f -> {
                long id = f.getId();
                FlashcardReview reviewCard = reviewRepository.findByflashcard_id(id);
                Assertions.assertNotNull(reviewCard);
                Assertions.assertEquals(reviewCard.getTimesReviewed(), 0);
                Assertions.assertEquals(reviewCard.getTimesRemembered(), 0);
                Assertions.assertEquals(reviewCard.getNextReview(), reviewCard.getLastReviewed());
                Assertions.assertEquals(reviewCard.getBucketType(), BucketType.One);
            });
        });
    }

    @Test
    public void givenDateGreaterThanLastReview_fetchSingleFlashcardToReview() {
        when(userService.getSignedOnUser()).thenReturn(TestUtils.getMockUser());

        Note newNote = TestUtils.createNote(2, "Flashcard test note", new Date());
        newNote.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 0));
        newNote.getCodeBlocks().add(TestUtils.createCodeBlock("abc", 0));

        newNote.getRichTextBlocks().forEach( rtb -> rtb.setNote(newNote));
        newNote.getCodeBlocks().forEach( cb -> cb.setNote(newNote));

        FlashcardBlock flashcardBlock = TestUtils.createFlashcardBlock(0, 2);
        flashcardBlock.getFlashcards().add(TestUtils.createFlashcard(0, "test question 2", "test answer 2"));

        newNote.getFlashcardBlocks().add(flashcardBlock);

        newNote.getFlashcardBlocks().forEach(fb -> {
            fb.setNote(newNote);
            fb.getFlashcards().forEach(f -> {
                FlashcardReview reviewSchedule = new FlashcardReview();
                reviewSchedule.setFlashcard(f);
                reviewSchedule.setBucketType(BucketType.Four);
                reviewSchedule.setNextReview(LocalDateTime.now().plusDays(5));
//                f.setUser(userService.getSignedOnUser());
                f.setReview(reviewSchedule);
                f.setFlashcardBlock(fb);
                f.setNote(newNote);
            });
        });

        noteRepository.save(newNote);

        LocalDateTime localDateTime = LocalDateTime.now();
        List<FlashcardInfo> reviewFlashcard = flashcardRepository.findAllByNoteUserIdAndReviewScheduleNextReviewLessThanEqual(TestUtils.getMockUser().getId(),
                localDateTime);
        Assertions.assertEquals(reviewFlashcard.size(), 1);
        Assertions.assertEquals(reviewFlashcard.get(0).getQuestion(), "test question");
    }
}
