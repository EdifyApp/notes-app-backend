package com.notes.api.controllers.responses;

import com.notes.api.responses.FlashcardInfo;

import java.util.List;

public class FlashcardInfoListResponse {

    private List<FlashcardInfo> flashcardInfoList;
    private boolean success;

    public FlashcardInfoListResponse(List<FlashcardInfo> flashcardInfoList, boolean success) {
        this.flashcardInfoList = flashcardInfoList;
        this.success = success;
    }

    public List<FlashcardInfo> getFlashcardInfoList() {
        return flashcardInfoList;
    }

    public void setFlashcardInfoList(List<FlashcardInfo> flashcardInfoList) {
        this.flashcardInfoList = flashcardInfoList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
