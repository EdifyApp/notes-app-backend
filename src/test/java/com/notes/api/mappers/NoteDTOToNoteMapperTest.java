package com.notes.api.mappers;

import com.notes.api.TestUtils;
import com.notes.api.dto.*;
import com.notes.api.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest(
        classes = {
                NoteDTOToNoteMapperImpl.class,
                BlockDTOToRichTextBlockMapperImpl.class,
                BlockDTOToCodeBlockMapperImpl.class,
                BlockDTOToFlashcardBlockMapperImpl.class,
                FlashcardDTOToFlashcardMapperImpl.class
        })
public class NoteDTOToNoteMapperTest {

    @Autowired
    protected NoteDTOToNoteMapper mapper;

    @Autowired
    BlockDTOToFlashcardBlockMapper flashcardBlockMapper;

    @Test
    public void givenNoteDTOToNote_whenMaps_thenCorrect() {
        NoteDTO noteDTO = TestUtils.createNoteDTO(1, "test note", new Date(2022, Calendar.JUNE, 6));
        noteDTO.getBlocks().add(TestUtils.createBlockDTO(BlockType.RichTextBlock, 2, "test data"));
        noteDTO.getBlocks().add(TestUtils.createBlockDTO(BlockType.CodeBlock, 3, "c = a + b"));

        Note note = mapper.toNote(noteDTO);

        Assertions.assertEquals(note.getNoteName(), noteDTO.getNoteName());
        Assertions.assertEquals(note.getId(), noteDTO.getId());
        Assertions.assertEquals(note.getLastSaved(), noteDTO.getLastSaved());

        Assertions.assertEquals(1, note.getRichTextBlocks().size());
        RichTextBlock block = note.getRichTextBlocks().get(0);
        Assertions.assertEquals(2, block.getId());
        Assertions.assertEquals("test data", block.getData());

        Assertions.assertEquals(1, note.getCodeBlocks().size());
        CodeBlock codeBlock = note.getCodeBlocks().get(0);
        Assertions.assertEquals(3, codeBlock.getId());
        Assertions.assertEquals("c = a + b", codeBlock.getData());
    }

    @Test
    public void givenNoteToNoteDTO_whenMaps_thenCorrect() {
        Note note = TestUtils.createNote(1, "test note", new Date(2022, Calendar.JUNE, 6));
        note.getRichTextBlocks().add(TestUtils.createRichTextBlock("test data", 2));
        note.getCodeBlocks().add(TestUtils.createCodeBlock("c = a + b", 3));

        NoteDTO noteDTO = mapper.toNoteDTO(note);

        Assertions.assertEquals(note.getNoteName(), noteDTO.getNoteName());
        Assertions.assertEquals(note.getId(), noteDTO.getId());
        Assertions.assertEquals(note.getLastSaved(), noteDTO.getLastSaved());

        Assertions.assertEquals(2, noteDTO.getBlocks().size());
        RichTextBlockDTO richTextBlockDTO = (RichTextBlockDTO) noteDTO.getBlocks().get(0);
        Assertions.assertEquals(BlockType.RichTextBlock, richTextBlockDTO.getType());
        Assertions.assertEquals(2, richTextBlockDTO.getId());
        Assertions.assertEquals("test data", richTextBlockDTO.getData());

        Assertions.assertEquals(2, noteDTO.getBlocks().size());
        CodeBlockDTO codeBlockDTO = (CodeBlockDTO) noteDTO.getBlocks().get(1);
        Assertions.assertEquals(BlockType.CodeBlock, codeBlockDTO.getType());
        Assertions.assertEquals(3, codeBlockDTO.getId());
        Assertions.assertEquals("c = a + b", codeBlockDTO.getData());
    }

    @Test
    public void givenFlashcardBlockDTOToFlashcardBlock_whenMaps_thenCorrect() {
        FlashcardBlockDTO flashcardBlockDTO = new FlashcardBlockDTO();
        flashcardBlockDTO.setId(1);

        List<FlashcardDTO> flashcardDTOs = new ArrayList<>();
        flashcardBlockDTO.setData(flashcardDTOs);
        flashcardDTOs.add(TestUtils.createFlashcardDTO(2, "what?", "yes"));
        flashcardDTOs.add(TestUtils.createFlashcardDTO(3, "why?", "idk"));

        FlashcardBlock flashcardBlock = flashcardBlockMapper.toFlashcardBlock(flashcardBlockDTO);

        Assertions.assertEquals(flashcardBlockDTO.getId(), flashcardBlock.getId());
        Assertions.assertEquals(2, flashcardBlock.getFlashcards().size());

        Flashcard flashcard = flashcardBlock.getFlashcards().get(1);
        Assertions.assertEquals(3, flashcard.getId());
        Assertions.assertEquals("why?", flashcard.getQuestion());
        Assertions.assertEquals("idk", flashcard.getAnswer());
    }

    @Test
    public void givenFlashcardBlockToFlashcardBlockDTO_whenMaps_thenCorrect() {
        FlashcardBlock flashcardBlock = new FlashcardBlock();
        flashcardBlock.setId(22);
        List<Flashcard> flashcards = new ArrayList<>();
        flashcardBlock.setFlashcards(flashcards);
        flashcards.add(TestUtils.createFlashcard(3, "what?", "yes"));
        flashcards.add(TestUtils.createFlashcard(4, "why?", "idk"));

        FlashcardBlockDTO flashcardBlockDTO = flashcardBlockMapper.toFlashcardBlockDTO(flashcardBlock);

        Assertions.assertEquals(flashcardBlock.getId(), flashcardBlockDTO.getId());
        Assertions.assertEquals(2, flashcardBlockDTO.getData().size());

        FlashcardDTO flashcardDTO = flashcardBlockDTO.getData().get(0);
        Assertions.assertEquals(3, flashcardDTO.getId());
        Assertions.assertEquals("what?", flashcardDTO.getQuestion());
        Assertions.assertEquals("yes", flashcardDTO.getAnswer());
    }
}
