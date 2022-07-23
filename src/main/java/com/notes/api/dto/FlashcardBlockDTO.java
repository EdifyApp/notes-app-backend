package com.notes.api.dto;

import java.util.List;

public class FlashcardBlockDTO extends BlockDTO {

    private List<FlashcardDTO> data;

    private String name;

    public FlashcardBlockDTO() {
       setType(BlockType.FlashcardBlock);
    }

    public List<FlashcardDTO> getData() {
        return data;
    }

    public void setData(List<FlashcardDTO> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
