package com.notes.api.entities;

import javax.persistence.*;

@Entity
public class CodeBlock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition="TEXT", name = "data")
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
