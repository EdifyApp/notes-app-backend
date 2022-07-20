package com.notes.api.mappers;

import com.notes.api.dto.BlockDTO;
import com.notes.api.dto.BlockType;
import com.notes.api.dto.CodeBlockDTO;
import com.notes.api.entities.CodeBlock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BlockDTOToCodeBlockMapper {

    public CodeBlock toCodeBlock(BlockDTO blockDTO) {
        if (! blockDTO.getType().equals(BlockType.CodeBlock)) {
            return null;
        }

        CodeBlockDTO codeBlockDTO = (CodeBlockDTO) blockDTO;
        CodeBlock codeBlock = new CodeBlock();
        codeBlock.setId(codeBlockDTO.getId());
        codeBlock.setData(codeBlockDTO.getData());
        codeBlock.setLocationIndex(codeBlockDTO.getLocationIndex());
        codeBlock.setFid(codeBlockDTO.getFid());
        return codeBlock;
    }

    public abstract List<CodeBlock> toCodeBlock(List<BlockDTO> blockDTOs);

    public BlockDTO toBlockDTO (CodeBlock codeBlock) {
        CodeBlockDTO blockDTO = new CodeBlockDTO();
        blockDTO.setId(codeBlock.getId());
        blockDTO.setData(codeBlock.getData());
        blockDTO.setLocationIndex(codeBlock.getLocationIndex());
        blockDTO.setFid(codeBlock.getFid());
        return blockDTO;
    }

    public abstract List<BlockDTO> toBlockDTOs(List<CodeBlock> codeBlocks);

}
