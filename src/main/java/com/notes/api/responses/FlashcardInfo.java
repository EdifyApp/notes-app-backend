package com.notes.api.responses;

public interface FlashcardInfo {
    long getId();
    long getFlashcardBlockId();
    long getNoteId();
    String getNoteNoteName();
    String getQuestion();
    String getAnswer();
}
