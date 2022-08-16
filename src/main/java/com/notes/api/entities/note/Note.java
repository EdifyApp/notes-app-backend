package com.notes.api.entities.note;

import com.notes.api.entities.User;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="note")
public class Note {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "note_name")
    private String noteName;

    @UpdateTimestamp
    private Date lastSaved;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "note", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RichTextBlock> richTextBlocks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "note", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FlashcardBlock> flashcardBlocks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "note", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CodeBlock> codeBlocks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
