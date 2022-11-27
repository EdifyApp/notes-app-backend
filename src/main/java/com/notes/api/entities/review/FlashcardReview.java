package com.notes.api.entities.review;

import com.notes.api.entities.User;
import com.notes.api.entities.note.Flashcard;
import com.notes.api.services.BucketType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="flashcardreviews")
public class FlashcardReview {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flashcard_id")
    private Flashcard flashcard;

    @Column(name="current_bucket")
    private BucketType bucketType = BucketType.One;

    @Column(name ="last_reviewed")
    private LocalDateTime lastReviewed = LocalDateTime.now();

    @Column(name="next_review")
    private LocalDateTime nextReview = lastReviewed.plusDays(1);

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

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public LocalDateTime getNextReview() {
        return nextReview;
    }

    public void setNextReview(LocalDateTime nextReview) {
        this.nextReview = nextReview;
    }

    public long getTimesRemembered() {
        return timesRemembered;
    }

    public void setTimesRemembered(long timesRemembered) {
        this.timesRemembered = timesRemembered;
    }

    public Flashcard getFlashcard(){
        return flashcard;
    }

    public long getFlashcardId() {
        return flashcard.getId();
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
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
