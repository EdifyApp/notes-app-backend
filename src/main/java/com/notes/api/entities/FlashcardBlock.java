package com.notes.api.entities;

import java.util.List;

public class FlashcardBlock extends Block {
    List<Flashcard> data;

    public FlashcardBlock() {
        setType(BlockType.FlashcardBlock);
    }

    public List<Flashcard> getData() {
        return data;
    }

    public void setData(List<Flashcard> data) {
        this.data = data;
    }
}
