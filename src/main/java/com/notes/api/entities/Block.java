package com.notes.api.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RichTextBlock.class, name = "richTextBlock"),
        @JsonSubTypes.Type(value = FlashcardBlock.class, name = "flashcardBlock"),
        @JsonSubTypes.Type(value = CodeBlock.class, name = "codeBlock")
})
public abstract class Block {

    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
