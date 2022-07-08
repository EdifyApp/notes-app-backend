package com.notes.api.mappers;

import com.notes.api.dto.*;
import com.notes.api.entities.CodeBlock;
import com.notes.api.entities.FlashcardBlock;
import com.notes.api.entities.Note;
import com.notes.api.entities.RichTextBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {
        BlockDTOToRichTextBlockMapper.class,
        BlockDTOToCodeBlockMapper.class,
        BlockDTOToFlashcardBlockMapper.class,
})
public abstract class NoteDTOToNoteMapper {

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

        List<BlockDTO> blockDTOs = toRichTextBlockDTOs(richTextBlocks);
        blockDTOs.addAll(toCodeBlockDTOs(codeBlocks));
        blockDTOs.addAll(toFlashcardBlockDTOs(flashcardBlocks));
        noteDTO.setBlocks(blockDTOs);
    }

    private List<BlockDTO> toRichTextBlockDTOs(List<RichTextBlock> richTextBlocks) {
        if (richTextBlocks == null || richTextBlocks.isEmpty()) {
            return Collections.emptyList();
        }

        return richTextBlocks.stream().map(richTextBlock -> {
            RichTextBlockDTO blockDTO = new RichTextBlockDTO();
            blockDTO.setData(richTextBlock.getData());
            blockDTO.setId(richTextBlock.getId());
            blockDTO.setLocationIndex(richTextBlock.getLocationIndex());
            return blockDTO;
        }).collect(Collectors.toList());
    }

    private List<BlockDTO> toCodeBlockDTOs(List<CodeBlock> codeBlocks) {
        if (codeBlocks == null || codeBlocks.isEmpty()) {
            return Collections.emptyList();
        }

        return codeBlocks.stream().map(codeBlock -> {
            CodeBlockDTO blockDTO = new CodeBlockDTO();
            blockDTO.setData(codeBlock.getData());
            blockDTO.setId(codeBlock.getId());
            blockDTO.setLocationIndex(codeBlock.getLocationIndex());
            return blockDTO;
        }).collect(Collectors.toList());
    }

    private List<BlockDTO> toFlashcardBlockDTOs(List<FlashcardBlock> flashcardBlocks) {
        if (flashcardBlocks == null || flashcardBlocks.isEmpty()) {
            return Collections.emptyList();
        }

        return flashcardBlocks.stream()
                .filter(flashcardBlock -> flashcardBlock.getFlashcards() != null)
                .map(flashcardBlock -> {
                    FlashcardBlockDTO blockDTO = new FlashcardBlockDTO();
                    blockDTO.setId(flashcardBlock.getId());
                    blockDTO.setLocationIndex(flashcardBlock.getLocationIndex());
                    blockDTO.setData(
                            flashcardBlock.getFlashcards().stream().map(flashcard -> {
                                FlashcardDTO flashcardDTO = new FlashcardDTO();
                                flashcardDTO.setQuestion(flashcard.getQuestion());
                                flashcardDTO.setAnswer(flashcard.getAnswer());
                                return flashcardDTO;
                            }).collect(Collectors.toList())
                    );
                    return blockDTO;
                }).collect(Collectors.toList());
    }
}
