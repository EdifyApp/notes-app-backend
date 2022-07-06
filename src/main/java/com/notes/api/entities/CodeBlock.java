package com.notes.api.entities;

import javax.persistence.*;

@Entity
public class CodeBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(columnDefinition="TEXT")
    String data;

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
}
