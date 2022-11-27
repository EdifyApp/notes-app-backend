package com.notes.api.controllers.responses;

import com.notes.api.dto.FlashcardReviewDTO;

/** Response sent back after a result is registered*/
public class FlashcardReviewResponse {

    private String message;

    private boolean success;

    public FlashcardReviewResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

}
