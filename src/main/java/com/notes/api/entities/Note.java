package com.notes.api.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String noteName;

    @Column(
            name = "last_saved",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private Date lastSaved;

    @OneToMany(cascade = CascadeType.ALL)
    List<RichTextBlock> richTextBlocks;

    @OneToMany(cascade = CascadeType.ALL)
    List<FlashcardBlock> flashcardBlocks;

    @OneToMany(cascade = CascadeType.ALL)
    List<CodeBlock> codeBlocks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Date getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(Date lastSaved) {
        this.lastSaved = lastSaved;
    }

    public List<RichTextBlock> getRichTextBlocks() {
        return richTextBlocks;
    }

    public void setRichTextBlocks(List<RichTextBlock> richTextBlocks) {
        this.richTextBlocks = richTextBlocks;
    }

    public List<FlashcardBlock> getFlashcardBlocks() {
        return flashcardBlocks;
    }

    public void setFlashcardBlocks(List<FlashcardBlock> flashcardBlocks) {
        this.flashcardBlocks = flashcardBlocks;
    }

    public List<CodeBlock> getCodeBlocks() {
        return codeBlocks;
    }

    public void setCodeBlocks(List<CodeBlock> codeBlocks) {
        this.codeBlocks = codeBlocks;
    }
}
