package com.notes.api.repositories;

import com.notes.api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    Note findById(long id);
    List<NoteInfo> findAllBy();
}
