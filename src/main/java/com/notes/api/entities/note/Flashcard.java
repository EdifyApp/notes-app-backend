package com.notes.api.entities.note;

import com.notes.api.entities.User;
import com.notes.api.entities.review.FlashcardReview;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Flashcard implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition="TEXT", name = "question")
    private String question;

    @Column(columnDefinition="TEXT", name = "answer")
    private String answer;

    @OneToOne(mappedBy = "flashcard", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private FlashcardReview reviewSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcardBlock_id")
    private FlashcardBlock flashcardBlock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

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

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public void setReview(FlashcardReview review) {
        this.reviewSchedule = review;
    }

    public FlashcardReview getReview() {
        return reviewSchedule;
    }
}
