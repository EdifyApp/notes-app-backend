package com.notes.api.controllers;

import com.notes.api.entities.Note;
import com.notes.api.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    // GET method to fetch all phones
    @GetMapping("/getallnotes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @PostMapping("/createnote")
    public ResponseEntity<String> createNote(@RequestBody Note note) {
        noteRepository.save(note);
        return ResponseEntity.status(HttpStatus.OK).body("Note created successfully!");
    }
}
