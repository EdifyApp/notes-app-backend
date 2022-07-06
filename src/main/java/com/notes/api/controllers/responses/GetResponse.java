package com.notes.api.controllers.responses;

import com.notes.api.dto.NoteDTO;

public class GetResponse {

    private NoteDTO note;
    private String message;
    private boolean success;

    public GetResponse(NoteDTO note, String message, boolean success) {
        this.note = note;
        this.message = message;
        this.success = success;
    }

    public NoteDTO getNote() {
        return note;
    }

    public void setNote(NoteDTO note) {
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
