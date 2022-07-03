package com.notes.api.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RichTextBlock.class, name = BlockType.Constants.RichTextBlock),
        @JsonSubTypes.Type(value = FlashcardBlock.class, name = BlockType.Constants.FlashcardBlock),
        @JsonSubTypes.Type(value = CodeBlock.class, name = BlockType.Constants.CodeBlock)
})
public abstract class Block {

    BlockType type;

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
}
