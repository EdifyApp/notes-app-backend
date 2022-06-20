package com.notes.api.controllers.responses;

import com.notes.api.entities.Note;

public class GetResponse {

    private Note note;
    private String message;
    private boolean success;

    public GetResponse(Note note, String message, boolean success) {
        this.note = note;
        this.message = message;
        this.success = success;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
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
