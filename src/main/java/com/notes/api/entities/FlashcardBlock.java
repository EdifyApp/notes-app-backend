package com.notes.api.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class FlashcardBlock implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flashcardBlock", fetch = FetchType.LAZY)
    private List<Flashcard> flashcards;

    @Column(name = "location_index")
    private long locationIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public long getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(long locationIndex) {
        this.locationIndex = locationIndex;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
