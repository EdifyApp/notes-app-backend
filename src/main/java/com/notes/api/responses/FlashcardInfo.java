package com.notes.api.responses;

public interface FlashcardInfo {
    long getId();
    long getFlashcardBlockId();
    String getQuestion();
    String getAnswer();
}
