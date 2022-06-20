package com.notes.api.controllers.responses;

import com.notes.api.repositories.NoteInfo;

import java.util.List;

public class NoteInfoListResponse {

    private List<NoteInfo> noteInfoList;
    private boolean success;

    public NoteInfoListResponse(List<NoteInfo> noteInfoList, boolean success) {
        this.noteInfoList = noteInfoList;
        this.success = success;
    }

    public List<NoteInfo> getNoteInfoList() {
        return noteInfoList;
    }

    public void setNoteInfoList(List<NoteInfo> noteInfoList) {
        this.noteInfoList = noteInfoList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
