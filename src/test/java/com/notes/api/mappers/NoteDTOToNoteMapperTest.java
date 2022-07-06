package com.notes.api.mappers;

import com.notes.api.dto.*;
import com.notes.api.entities.CodeBlock;
import com.notes.api.entities.Note;
import com.notes.api.entities.RichTextBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {NoteDTOToNoteMapperImpl.class})
public class NoteDTOToNoteMapperTest {

    @Autowired
    protected NoteDTOToNoteMapper mapper;

    @Test
    public void givenNoteDTOToNote_whenMaps_thenCorrect() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteName("note name");
        noteDTO.setId(1);
        noteDTO.setLastSaved(new Date(2022, Calendar.JUNE,5));

        List<BlockDTO> blockDTOList = new ArrayList<>();
        noteDTO.setBlocks(blockDTOList);
        blockDTOList.add(createBlockDTO(BlockType.RichTextBlock, 2, "test data"));
        blockDTOList.add(createBlockDTO(BlockType.CodeBlock, 3, "c = a + b"));

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
        Note note = new Note();
        note.setNoteName("test note");
        note.setId(1);
        note.setLastSaved(new Date(2022, Calendar.JUNE, 5));

        List<RichTextBlock> richTextBlocks = new ArrayList<>();
        richTextBlocks.add(createRichTextBlock("test data", 2));
        note.setRichTextBlocks(richTextBlocks);

        List<CodeBlock> codeBlocks = new ArrayList<>();
        codeBlocks.add(createCodeBlock("c = a + b", 3));
        note.setCodeBlocks(codeBlocks);

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

    private BlockDTO createBlockDTO(BlockType type, long id, String data) {
        if (type == BlockType.RichTextBlock) {
            RichTextBlockDTO richTextBlockDTO = new RichTextBlockDTO();
            richTextBlockDTO.setData(data);
            richTextBlockDTO.setId(id);
            return richTextBlockDTO;
        } else if (type == BlockType.CodeBlock) {
            CodeBlockDTO codeBlockDTO = new CodeBlockDTO();
            codeBlockDTO.setData(data);
            codeBlockDTO.setId(id);
            return  codeBlockDTO;
        }
        return null;
    }

    private RichTextBlock createRichTextBlock(String data, long id) {
        RichTextBlock richTextBlock = new RichTextBlock();
        richTextBlock.setId(id);
        richTextBlock.setData(data);
        return richTextBlock;
    }

    private CodeBlock createCodeBlock(String data, long id) {
        CodeBlock codeBlock = new CodeBlock();
        codeBlock.setData(data);
        codeBlock.setId(3);
        return codeBlock;
    }
}
