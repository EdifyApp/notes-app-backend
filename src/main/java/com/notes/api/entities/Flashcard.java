package com.notes.api.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Flashcard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition="TEXT")
    private String question;

    @Column(columnDefinition="TEXT")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "flashcardBlock_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FlashcardBlock flashcardBlock;

    @ManyToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Note note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FlashcardBlock getFlashcardBlock() {
        return flashcardBlock;
    }

    public void setFlashcardBlock(FlashcardBlock flashcardBlock) {
        this.flashcardBlock = flashcardBlock;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
