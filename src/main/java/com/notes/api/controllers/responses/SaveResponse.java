package com.notes.api.controllers.responses;

import com.notes.api.dto.NoteDTO;

public class SaveResponse {

    private NoteDTO savedNote;
    private String message;
    private boolean success;

    public SaveResponse(NoteDTO savedNote, String message, boolean success) {
        this.savedNote = savedNote;
        this.message = message;
        this.success = success;
    }

    public NoteDTO getNoteDTO() {
        return savedNote;
    }

    public void setNoteDTO(NoteDTO savedNote) {
        this.savedNote = savedNote;
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
