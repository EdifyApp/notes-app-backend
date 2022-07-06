package com.notes.api.mappers;

import com.notes.api.dto.BlockDTO;
import com.notes.api.dto.BlockType;
import com.notes.api.dto.FlashcardBlockDTO;
import com.notes.api.entities.FlashcardBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BlockDTOToFlashcardBlockMapper {

    @Autowired
    protected FlashcardDTOToFlashcardMapper mapper;

    public FlashcardBlock toFlashcardBlock(BlockDTO blockDTO) {
        if (! blockDTO.getType().equals(BlockType.FlashcardBlock)) {
            return null;
        }

        FlashcardBlockDTO flashcardBlockDTO = (FlashcardBlockDTO) blockDTO;
        FlashcardBlock flashcardBlock = new FlashcardBlock();
        flashcardBlock.setId(flashcardBlockDTO.getId());
        flashcardBlock.setFlashcards(mapper.toFlashcards(flashcardBlockDTO.getData()));
        return flashcardBlock;
    }

    public abstract List<FlashcardBlock> toFlashcardBlocks(List<BlockDTO> flashcardBlockDTOS);

    public FlashcardBlockDTO toFlashcardBlockDTO(FlashcardBlock flashcardBlock) {
        FlashcardBlockDTO flashcardBlockDTO = new FlashcardBlockDTO();
        flashcardBlockDTO.setId(flashcardBlock.getId());
        flashcardBlockDTO.setData(mapper.toFlashcardDTOs(flashcardBlock.getFlashcards()));
        return flashcardBlockDTO;
    }

    public abstract List<BlockDTO> toFlashcardBlockDTOs(List<FlashcardBlock> flashcardBlocks);

    @AfterMapping
    public void setType(@MappingTarget FlashcardBlockDTO flashcardBlockDTO) {
        flashcardBlockDTO.setType(BlockType.FlashcardBlock);
    }
}
