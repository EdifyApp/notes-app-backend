package com.notes.api.dto;

import java.util.Date;

public class FlashcardReviewDTO {

    private long flashcardId;

    private Date lastReviewed;

    private Boolean remembered;

    public long getFlashcardId() { return flashcardId; }

    public void setFlashcardId(long flashcardId) { this.flashcardId = flashcardId; }

    public Date getLastReviewed() { return lastReviewed; }

    public void setLastReviewed(Date lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public void setRemembered(Boolean remembered) {
        this.remembered = remembered;
    }

    public Boolean getRemembered() {
        return remembered;
    }
}
