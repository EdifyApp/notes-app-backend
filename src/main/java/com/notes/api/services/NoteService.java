package com.notes.api.services;

import com.notes.api.dto.BlockDTO;
import com.notes.api.dto.NoteDTO;
import com.notes.api.entities.Note;
import com.notes.api.mappers.NoteDTOToNoteMapper;
import com.notes.api.repositories.FlashcardRepository;
import com.notes.api.repositories.NoteRepository;
import com.notes.api.responses.FlashcardInfo;
import com.notes.api.responses.NoteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NoteService {

    NoteRepository noteRepository;

    NoteDTOToNoteMapper mapper;

    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    public NoteService(NoteRepository repository, NoteDTOToNoteMapper mapper) {
        this.mapper = mapper;
        this.noteRepository = repository;
    }

    public NoteDTO getNoteById(long id) {
        Note note = noteRepository.findById(id);
        NoteDTO noteDTO = mapper.toNoteDTO(note);

        if (noteDTO == null) {
            return null;
        }

        noteDTO.getBlocks().sort(Comparator.comparing(BlockDTO::getLocationIndex));
        return noteDTO;
    }

    public List<NoteInfo> getAllNoteInfo() {
        return noteRepository.findAllBy();
    }

    public long saveNote(NoteDTO noteDTO) {
        Note note = mapper.toNote(noteDTO);
        linkBlocksToNote(note);
        noteRepository.save(note);
        return note.getId();
    }

    public List<FlashcardInfo> getAllFlashcardInfo() { return  flashcardRepository.findAllBy(); }

    private void linkBlocksToNote(Note note) {
        note.getFlashcardBlocks().forEach(fb -> {
            fb.setNote(note);
            fb.getFlashcards().forEach(f -> {
                f.setFlashcardBlock(fb);
                f.setNote(note);
            });
        });

        note.getRichTextBlocks().forEach( rtb -> rtb.setNote(note));
        note.getCodeBlocks().forEach( cb -> cb.setNote(note));
    }
}
