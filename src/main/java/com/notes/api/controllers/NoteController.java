package com.notes.api.controllers;

import com.notes.api.entities.Note;
import com.notes.api.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/getnote")
    @ResponseBody
    public GetResponse getNoteById(@RequestParam long id) {
        Note note = noteRepository.findById(id);

        if (note != null) {
            return new GetResponse(note, "found note", true);
        }

        return new GetResponse(null, "could not find note with specified id", false);
    }

    @PostMapping("/createnote")
    @ResponseBody
    public SaveResponse createNote(@RequestBody Note note) {
        noteRepository.save(note);
        return new SaveResponse("Note successfully save", true);
    }
}
