package com.notes.api.controllers.responses;

import com.notes.api.dto.NoteDTO;

public class DeleteResponse {
    private String message;
    private boolean success;

    public DeleteResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
