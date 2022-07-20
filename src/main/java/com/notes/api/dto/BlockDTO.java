package com.notes.api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RichTextBlockDTO.class, name = BlockType.Constants.RichTextBlock),
        @JsonSubTypes.Type(value = FlashcardBlockDTO.class, name = BlockType.Constants.FlashcardBlock),
        @JsonSubTypes.Type(value = CodeBlockDTO.class, name = BlockType.Constants.CodeBlock)
})
public abstract class BlockDTO {
    private long id;
    private BlockType type;
    private long locationIndex;

    private String fid;

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

    public long getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(long locationIndex) {
        this.locationIndex = locationIndex;
    }

    public String getFid() { return fid; }

    public void setFid(String fid) { this.fid = fid; }
}
