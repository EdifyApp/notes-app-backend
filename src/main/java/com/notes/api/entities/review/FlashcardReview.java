package com.notes.api.entities.review;

import com.notes.api.entities.User;
import com.notes.api.entities.note.Flashcard;
import com.notes.api.services.BucketType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="flashcardreviews")
public class FlashcardReview {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="flashcard_id", referencedColumnName = "id")
    private Flashcard flashcardId;

    @Column(name="current_bucket")
    private BucketType bucketType;

    @Column(name ="last_reviewed")
    private Date lastReviewed;

    @Column(name="next_review")
    private Date nextReview;

    @Column(name="times_reviewed")
    private long timesReviewed;

    @Column(name="times_remembered")
    private long timesRemembered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public BucketType getBucketType() {
        return bucketType;
    }

    public void setBucketType(BucketType bucketType) {
        this.bucketType = bucketType;
    }

    public Date getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(Date lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public Date getNextReview() {
        return nextReview;
    }

    public void setNextReview(Date nextReview) {
        this.nextReview = nextReview;
    }

    public long getTimesRemembered() {
        return timesRemembered;
    }

    public void setTimesRemembered(long timesRemembered) {
        this.timesRemembered = timesRemembered;
    }

    public Flashcard getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(Flashcard flashcardId) {
        this.flashcardId = flashcardId;
    }

    public long getTimesReviewed() {
        return timesReviewed;
    }

    public void setTimesReviewed(long timesReviewed) {
        this.timesReviewed = timesReviewed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
