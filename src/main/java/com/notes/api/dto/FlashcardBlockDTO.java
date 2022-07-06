package com.notes.api.dto;

import java.util.List;

public class FlashcardBlockDTO extends BlockDTO {

    private long id;
    private List<FlashcardDTO> data;

    public FlashcardBlockDTO() {
       setType(BlockType.FlashcardBlock);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FlashcardDTO> getData() {
        return data;
    }

    public void setData(List<FlashcardDTO> data) {
        this.data = data;
    }
}
