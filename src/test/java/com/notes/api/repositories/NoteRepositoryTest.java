package com.notes.api.repositories;

import com.notes.api.TestUtils;
import com.notes.api.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NoteRepositoryTest {

    @Autowired
    NoteRepository noteRepository;

    @BeforeEach
    public void saveNote() {
        Note note = TestUtils.createNote(0, "test note", new Date());
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("abc", 0, 4));
        note.getCodeBlocks().add(TestUtils.createCodeBlock("abc", 0));

        FlashcardBlock flashcardBlock = TestUtils.createFlashcardBlock(0, 3);
        flashcardBlock.getFlashcards().add(TestUtils.createFlashcard(0, "test question", "test answer"));
        note.getFlashcardBlocks().add(flashcardBlock);

        note.getFlashcardBlocks().forEach(fb -> {
            fb.setNote(note);
            fb.getFlashcards().forEach(f -> {
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

    @Test
    @Transactional
    public void givenSavedNote_whenDeleteNoteById_thenNoteCannotBeFoundInDb() {
        noteRepository.deleteById(1);
        Assertions.assertNull(noteRepository.findById(1));
    }

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

}
