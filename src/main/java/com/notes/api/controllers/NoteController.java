package com.notes.api.controllers;

import com.notes.api.controllers.responses.GetResponse;
import com.notes.api.controllers.responses.NoteInfoListResponse;
import com.notes.api.controllers.responses.SaveResponse;
import com.notes.api.dto.NoteDTO;
import com.notes.api.entities.Note;
import com.notes.api.mappers.NoteDTOToNoteMapper;
import com.notes.api.repositories.NoteRepository;
import com.notes.api.responses.NoteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    NoteDTOToNoteMapper mapper;

    @GetMapping("/getnote")
    @ResponseBody
    public GetResponse getNoteById(@RequestParam long id) {
        Note note = noteRepository.findById(id);
        NoteDTO noteDTO = mapper.toNoteDTO(note);
        if (note != null) {
            return new GetResponse(noteDTO, "found note", true);
        }

        return new GetResponse(null, "could not find note with specified id", false);
    }

    @GetMapping("/getallnoteinfo")
    @ResponseBody
    public NoteInfoListResponse getAllNoteInfo() {
        List<NoteInfo> noteInfoList = noteRepository.findAllBy();
        return new NoteInfoListResponse(noteInfoList, true);
    }

    @PostMapping("/savenote")
    @ResponseBody
    public SaveResponse createNote(@RequestBody NoteDTO noteDTO) {
        noteDTO.setLastSaved(new Date());
        Note note = mapper.toNote(noteDTO);
        noteRepository.save(note);
        return new SaveResponse(note.getId(), "Note successfully save", true);
    }
}
