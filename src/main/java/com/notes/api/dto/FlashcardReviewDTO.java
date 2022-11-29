package com.notes.api.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class FlashcardReviewDTO {

    private long flashcardId;

    private Boolean remembered;

    public long getFlashcardId() { return flashcardId; }

    public void setFlashcardId(long flashcardId) { this.flashcardId = flashcardId; }

    public void setRemembered(Boolean remembered) {
        this.remembered = remembered;
    }

    public Boolean getRemembered() {
        return remembered;
    }
}
