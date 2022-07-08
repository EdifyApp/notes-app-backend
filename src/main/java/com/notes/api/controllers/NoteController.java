package com.notes.api.controllers;

import com.notes.api.controllers.responses.GetResponse;
import com.notes.api.controllers.responses.NoteInfoListResponse;
import com.notes.api.controllers.responses.SaveResponse;
import com.notes.api.dto.NoteDTO;
import com.notes.api.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/getnote")
    @ResponseBody
    public GetResponse getNoteById(@RequestParam long id) {
        NoteDTO noteDTO = noteService.getNoteById(id);
        if (noteDTO != null) {
            return new GetResponse(noteDTO, "found note", true);
        }
        return new GetResponse(null, "could not find note with specified id", false);
    }

    @GetMapping("/getallnoteinfo")
    @ResponseBody
    public NoteInfoListResponse getAllNoteInfo() {
        return new NoteInfoListResponse(noteService.getAllNoteInfo(), true);
    }

    @PostMapping("/savenote")
    @ResponseBody
    public SaveResponse createNote(@RequestBody NoteDTO noteDTO) {
        long noteId = noteService.saveNote(noteDTO);
        return new SaveResponse(noteId, "Note successfully save", true);
    }
}
