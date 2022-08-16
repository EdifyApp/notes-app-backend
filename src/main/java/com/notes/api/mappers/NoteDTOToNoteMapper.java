package com.notes.api.mappers;

import com.notes.api.dto.BlockDTO;
import com.notes.api.dto.NoteDTO;
import com.notes.api.entities.note.CodeBlock;
import com.notes.api.entities.note.FlashcardBlock;
import com.notes.api.entities.note.Note;
import com.notes.api.entities.note.RichTextBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
        BlockDTOToRichTextBlockMapper.class,
        BlockDTOToCodeBlockMapper.class,
        BlockDTOToFlashcardBlockMapper.class,
})
public abstract class NoteDTOToNoteMapper {

    @Autowired
    BlockDTOToRichTextBlockMapper richTextBlockMapper;

    @Autowired
    BlockDTOToCodeBlockMapper codeBlockMapper;

    @Autowired
    BlockDTOToFlashcardBlockMapper flashcardBlockMapper;

    @Mapping(source = "blocks", target = "richTextBlocks")
    @Mapping(source = "blocks", target = "codeBlocks")
    @Mapping(source = "blocks", target = "flashcardBlocks")
    public abstract Note toNote(NoteDTO noteDTO);

    @Mapping(target = "blocks", ignore = true)
    public abstract NoteDTO toNoteDTO(Note note);

    @AfterMapping
    public void removeNulls(@MappingTarget Note note) {
        note.getRichTextBlocks().removeAll(Collections.singleton(null));
        note.getCodeBlocks().removeAll(Collections.singleton(null));
        note.getFlashcardBlocks().removeAll(Collections.singleton(null));
    }

    @AfterMapping
    public void toBlockDTOs(@MappingTarget NoteDTO noteDTO, Note note) {
        List<RichTextBlock> richTextBlocks = note.getRichTextBlocks();
        List<CodeBlock> codeBlocks = note.getCodeBlocks();
        List<FlashcardBlock> flashcardBlocks = note.getFlashcardBlocks();

        List<BlockDTO> blockDTOs = richTextBlockMapper.toBlockDTOs(richTextBlocks);
        blockDTOs.addAll(codeBlockMapper.toBlockDTOs(codeBlocks));
        blockDTOs.addAll(flashcardBlockMapper.toFlashcardBlockDTOs(flashcardBlocks));
        noteDTO.setBlocks(blockDTOs);
    }
}
