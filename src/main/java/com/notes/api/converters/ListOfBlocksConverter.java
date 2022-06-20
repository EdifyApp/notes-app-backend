package com.notes.api.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes.api.entities.Block;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class ListOfBlocksConverter implements AttributeConverter<List<Block>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Block> blocks) {

        String blocksJsonString;
        try {
            blocksJsonString = objectMapper.writeValueAsString(blocks);
        } catch (final JsonProcessingException e) {
            return  "";
        }

        return blocksJsonString;
    }

    @Override
    public List<Block> convertToEntityAttribute(String blocksJsonString) {

        List<Block> blocks;
        try {
            blocks = objectMapper.readValue(blocksJsonString, new TypeReference<List<Block>>(){});
        } catch (final JsonProcessingException e) {
            return new ArrayList<>();
        }

        return blocks;
    }
}
