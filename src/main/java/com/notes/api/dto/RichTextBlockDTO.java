package com.notes.api.dto;

public class RichTextBlockDTO extends BlockDTO {

    private String data;

    public RichTextBlockDTO() {
        setType(BlockType.RichTextBlock);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
