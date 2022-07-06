package com.notes.api.mappers;

import com.notes.api.dto.BlockDTO;
import com.notes.api.dto.BlockType;
import com.notes.api.dto.RichTextBlockDTO;
import com.notes.api.entities.RichTextBlock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BlockDTOToRichTextBlockMapper {

    public RichTextBlock toRichTextBlock(BlockDTO blockDTO) {
        if (! blockDTO.getType().equals(BlockType.RichTextBlock)) {
            return null;
        }

        RichTextBlockDTO richTextBlockDTO = (RichTextBlockDTO) blockDTO;
        RichTextBlock richTextBlock = new RichTextBlock();
        richTextBlock.setId(richTextBlockDTO.getId());
        richTextBlock.setData(richTextBlockDTO.getData());
        return richTextBlock;
    }

    public abstract List<RichTextBlock> toRichTextBlock(List<BlockDTO> blockDTOS);

    public BlockDTO toBlockDTO(RichTextBlock richTextBlock) {
        RichTextBlockDTO blockDTO = new RichTextBlockDTO();
        blockDTO.setId(richTextBlock.getId());
        blockDTO.setData(richTextBlock.getData());
        return blockDTO;
    }

    public abstract List<BlockDTO> toBlockDTOs(List<RichTextBlock> richTextBlocks);

}
