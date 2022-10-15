package com.notes.api.repositories;

import com.notes.api.entities.note.Note;
import com.notes.api.responses.NoteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    Integer deleteById(long id);
    Note findById(long id);
    Note findByIdAndUserId(long id, String userId);
    List<NoteInfo> findAllByUserId(String userId);
}
