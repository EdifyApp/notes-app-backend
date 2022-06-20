package com.notes.api.controllers.responses;

public class SaveResponse {

    private long noteId;
    private String message;
    private boolean success;

    public SaveResponse(long noteId, String message, boolean success) {
        this.noteId = noteId;
        this.message = message;
        this.success = success;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
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
