package com.notes.api.repositories;

import com.notes.api.TestUtils;
import com.notes.api.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class NoteRepositoryTest {

    @Autowired
    NoteRepository noteRepository;

    @Test
    public void givenNoteDTO_whenSaved_thenSameNoteDTOReturned() {
        Note note = TestUtils.createNote(0, "test note", new Date());
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 4));
        note.getCodeBlocks().add(TestUtils.createCodeBlock("abc", 0));

        FlashcardBlock flashcardBlock = TestUtils.createFlashcardBlock(0, 3);
        flashcardBlock.getFlashcards().add(TestUtils.createFlashcard(0, "test question", "test answer"));
        note.getFlashcardBlocks().add(flashcardBlock);

        noteRepository.save(note);

        Note noteFromDb = noteRepository.findById(1);

        Assertions.assertNotNull(noteFromDb);
        Assertions.assertEquals(1L, noteFromDb.getId() );

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
        Assertions.assertEquals(flashcardBlockFromDb.getId(), flashcardFromDb.getFlashcardBlock().getId());
    }
}
