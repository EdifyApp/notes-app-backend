package com.notes.api.services;

import com.google.auto.value.AutoOneOf;
import com.notes.api.dto.BlockDTO;
import com.notes.api.dto.NoteDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.note.Note;
import com.notes.api.entities.review.FlashcardReview;
import com.notes.api.mappers.NoteDTOToNoteMapper;
import com.notes.api.repositories.FlashcardRepository;
import com.notes.api.repositories.NoteRepository;
import com.notes.api.repositories.ReviewRepository;
import com.notes.api.repositories.UserRepository;
import com.notes.api.responses.FlashcardInfo;
import com.notes.api.responses.NoteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class NoteService {

    NoteRepository noteRepository;

    NoteDTOToNoteMapper mapper;

    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    public NoteService(NoteRepository repository, NoteDTOToNoteMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.noteRepository = repository;
        this.userService = userService;
    }

    public NoteDTO getNoteById(long id) {
        User user = userService.getSignedOnUser();
        Note note = noteRepository.findByIdAndUserId(id, user.getId());
        NoteDTO noteDTO = mapper.toNoteDTO(note);

        if (noteDTO == null) {
            return null;
        }

        noteDTO.getBlocks().sort(Comparator.comparing(BlockDTO::getLocationIndex));
        return noteDTO;
    }

    @Transactional
    public Integer deleteNoteById(long id) {
        return noteRepository.deleteById(id);
    }

    public List<NoteInfo> getAllNoteInfo() {
        String userId = userService.getSignedOnUser().getId();
        return noteRepository.findAllByUserId(userId);
    }

    public NoteDTO saveNote(NoteDTO noteDTO) {
        User user = userService.getSignedOnUser();
        Note note = mapper.toNote(noteDTO);
        note.setUser(user);
        linkBlocksToNote(note, user);
        noteRepository.save(note);
        NoteDTO savedNoteDTO = mapper.toNoteDTO(note);
        savedNoteDTO.getBlocks().sort(Comparator.comparing(BlockDTO::getLocationIndex));
        return savedNoteDTO;
    }

    public List<FlashcardInfo> getAllFlashcardInfo() {
        User user = userService.getSignedOnUser();
        return flashcardRepository.findAllByNoteUserId(user.getId());
    }

    private void linkBlocksToNote(Note note, User user) {
        note.getFlashcardBlocks().forEach(fb -> {
            fb.setNote(note);
            fb.getFlashcards().forEach(f -> {
                long id = f.getId();
                if (reviewRepository.findByflashcard_id(id) == null) {
                    FlashcardReview reviewSchedule = new FlashcardReview();
                    reviewSchedule.setUser(user);
                    reviewSchedule.setFlashcard(f);
                    f.setReview(reviewSchedule);
                }
                f.setFlashcardBlock(fb);
                f.setNote(note);
            });
        });

        note.getRichTextBlocks().forEach( rtb -> rtb.setNote(note));
        note.getCodeBlocks().forEach( cb -> cb.setNote(note));
    }
}
