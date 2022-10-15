package com.notes.api.services;

import com.notes.api.TestUtils;
import com.notes.api.dto.NoteDTO;
import com.notes.api.entities.User;
import com.notes.api.entities.note.Note;
import com.notes.api.mappers.*;
import com.notes.api.repositories.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = {
                NoteDTOToNoteMapperImpl.class,
                BlockDTOToRichTextBlockMapperImpl.class,
                BlockDTOToCodeBlockMapperImpl.class,
                BlockDTOToFlashcardBlockMapperImpl.class,
                FlashcardDTOToFlashcardMapperImpl.class
        }
)
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Autowired
    NoteDTOToNoteMapper mapper;

    @Mock
    NoteRepository noteRepository;

    @Mock
    UserService userService;

    NoteService noteService;

    @BeforeEach
    public void setUp() {
        noteService = new NoteService(noteRepository, mapper, userService);
    }

    @Test
    public void givenNote_whenRequested_thenNoteDTOBlocksSorted() {
        Note note = TestUtils.createNote(1, "test note", new Date());
        note.setUser(getMockUser());
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 2, 4));
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 1, 1));
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 2));
        note.getFlashcardBlocks().add(TestUtils.createFlashcardBlock(3, 3));
        note.getFlashcardBlocks().add(TestUtils.createFlashcardBlock(4, 0));

        when(noteRepository.findByIdAndUserId(anyLong(), anyString())).thenReturn(note);
        when(userService.getSignedOnUser()).thenReturn(getMockUser());

        NoteDTO noteDTO = noteService.getNoteById(1L);

        Assertions.assertTrue(TestUtils.isBlocksSorted(noteDTO));
    }

    @Test
    public void givenNullNoteDTO_whenRequested_thenNoError() {
        when(noteRepository.findByIdAndUserId(anyLong(), anyString())).thenReturn(null);
        when(userService.getSignedOnUser()).thenReturn(getMockUser());

        NoteDTO noteDTO = noteService.getNoteById(2L);

        Assertions.assertNull(noteDTO);
    }

    private User getMockUser() {
        User user = new User();
        user.setId("firebase-id");
        return user;
    }
}
