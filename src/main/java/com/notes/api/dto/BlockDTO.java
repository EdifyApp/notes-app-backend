package com.notes.api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.notes.api.entities.CodeBlock;
import com.notes.api.entities.FlashcardBlock;
import com.notes.api.entities.RichTextBlock;

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
public abstract class BlockDTO {
    private long id;
    private BlockType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }
}
