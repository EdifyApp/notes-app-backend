package com.notes.api.mappers;

import com.notes.api.dto.*;
import com.notes.api.entities.CodeBlock;
import com.notes.api.entities.Note;
import com.notes.api.entities.RichTextBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {
        BlockDTOToRichTextBlockMapper.class,
        BlockDTOToCodeBlockMapper.class,
//        BlockDTOToFlashcardBlockMapper.class,
})
public abstract class NoteDTOToNoteMapper {

    @Mapping(source = "blocks", target = "richTextBlocks")
    @Mapping(source = "blocks", target = "codeBlocks")
//    @Mapping(source = "blocks", target = "flashcardBlocks")
    public abstract Note toNote(NoteDTO noteDTO);

    @Mapping(target = "blocks", ignore = true)
    public abstract NoteDTO toNoteDTO(Note note);

    @AfterMapping
    public void removeNulls(@MappingTarget Note note) {
        note.getRichTextBlocks().removeAll(Collections.singleton(null));
        note.getCodeBlocks().removeAll(Collections.singleton(null));
    }

    @AfterMapping
    public void toBlockDTOs(@MappingTarget NoteDTO noteDTO, Note note) {
        List<RichTextBlock> richTextBlocks = note.getRichTextBlocks();
        List<CodeBlock> codeBlocks = note.getCodeBlocks();

        List<BlockDTO> blockDTOs = richTextBlocks.stream().map(richTextBlock -> {
            RichTextBlockDTO blockDTO = new RichTextBlockDTO();
            blockDTO.setType(BlockType.RichTextBlock);
            blockDTO.setData(richTextBlock.getData());
            blockDTO.setId(richTextBlock.getId());
            return blockDTO;
        }).collect(Collectors.toList());

        blockDTOs.addAll(
                codeBlocks.stream().map(codeBlock -> {
                    CodeBlockDTO blockDTO = new CodeBlockDTO();
                    blockDTO.setType(BlockType.CodeBlock);
                    blockDTO.setData(codeBlock.getData());
                    blockDTO.setId(codeBlock.getId());
                    return blockDTO;
                }).collect(Collectors.toList())
        );

        noteDTO.setBlocks(blockDTOs);
    }
}
