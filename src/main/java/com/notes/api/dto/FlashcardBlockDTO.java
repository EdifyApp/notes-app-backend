package com.notes.api.dto;

import java.util.List;

public class FlashcardBlockDTO extends BlockDTO {

    private List<FlashcardDTO> data;

    public FlashcardBlockDTO() {
       setType(BlockType.FlashcardBlock);
    }

    public List<FlashcardDTO> getData() {
        return data;
    }

    public void setData(List<FlashcardDTO> data) {
        this.data = data;
    }
}
