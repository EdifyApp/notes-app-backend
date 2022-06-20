package com.notes.api.entities;

import com.notes.api.converters.ListOfBlocksConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String noteName;

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
}
