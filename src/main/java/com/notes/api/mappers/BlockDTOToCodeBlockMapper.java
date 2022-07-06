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
        return codeBlock;
    }

    public abstract List<CodeBlock> toCodeBlock(List<BlockDTO> blockDTOs);

    public BlockDTO toBlockDTO (CodeBlock codeBlock) {
        CodeBlockDTO blockDTO = new CodeBlockDTO();
        blockDTO.setId(codeBlock.getId());
        blockDTO.setData(codeBlock.getData());
        return blockDTO;
    }

    public abstract List<BlockDTO> toBlockDTOs(List<CodeBlock> codeBlocks);

}
