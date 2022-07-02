package com.notes.api.entities;

import com.notes.api.converters.ListOfBlocksConverter;

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

    @Convert(converter = ListOfBlocksConverter.class)
    @Column(columnDefinition="TEXT")
    private List<Block> blocks;


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

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Date getLastSaved() {
        return lastSaved;
    }

    public void setLastSaved(Date lastSaved) {
        this.lastSaved = lastSaved;
    }
}
