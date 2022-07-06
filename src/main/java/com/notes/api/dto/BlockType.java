package com.notes.api.dto;

public enum BlockType {
    RichTextBlock,
    FlashcardBlock,
    CodeBlock;

    public static class Constants {
        public static final String RichTextBlock = "RichTextBlock";
        public static final String FlashcardBlock = "FlashcardBlock";
        public static final String CodeBlock = "CodeBlock";
    }
}
