package com.notes.api.entities;

public class CodeBlock extends Block {
    String data;

    public CodeBlock() {
        setType(BlockType.CodeBlock);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
