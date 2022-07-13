package com.notes.api.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class FlashcardBlock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "flashcardBlock_id", referencedColumnName = "id"),
        @JoinColumn(name = "note_id", referencedColumnName = "note_id")
    })
    private List<Flashcard> flashcards;

    @Column
    private long locationIndex;

    @ManyToOne
    @JoinColumn(name = "note_id", insertable = false, updatable = false)
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
