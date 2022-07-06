package com.notes.api.dto;

public class CodeBlockDTO extends BlockDTO {

    private String data;

    public CodeBlockDTO() {
        setType(BlockType.CodeBlock);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
