package com.notes.api;

import com.notes.api.dto.*;
import com.notes.api.entities.User;
import com.notes.api.entities.note.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static NoteDTO createNoteDTO(long id, String noteName, Date lastSaved) {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteName(noteName);
        noteDTO.setLastSaved(lastSaved);
        noteDTO.setId(id);
        noteDTO.setBlocks(new ArrayList<>());
        return noteDTO;
    }

    public static Note createNote(long id, String noteName, Date lastSaved) {
        Note note = new Note();
        note.setNoteName(noteName);
        note.setId(id);
        note.setLastSaved(lastSaved);
        note.setCodeBlocks(new ArrayList<>());
        note.setRichTextBlocks(new ArrayList<>());
        note.setFlashcardBlocks(new ArrayList<>());
        return note;
    }

    public static BlockDTO createBlockDTO(BlockType type, long id, String data) {
        return createBlockDTO(type, id, data, 0);
    }

    public static BlockDTO createBlockDTO(BlockType type, long id, String data, long locationIndex) {
        if (type == BlockType.RichTextBlock) {
            RichTextBlockDTO richTextBlockDTO = new RichTextBlockDTO();
            richTextBlockDTO.setData(data);
            richTextBlockDTO.setId(id);
            richTextBlockDTO.setLocationIndex(locationIndex);
            return richTextBlockDTO;
        } else if (type == BlockType.CodeBlock) {
            CodeBlockDTO codeBlockDTO = new CodeBlockDTO();
            codeBlockDTO.setData(data);
            codeBlockDTO.setId(id);
            codeBlockDTO.setLocationIndex(locationIndex);
            return  codeBlockDTO;
        }
        return null;
    }

    public static FlashcardDTO createFlashcardDTO(long id, String question, String answer) {
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setQuestion(question);
        flashcardDTO.setAnswer(answer);
        flashcardDTO.setId(id);
        return flashcardDTO;
    }

    public static Flashcard createFlashcard(long id, String question, String answer) {
        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion(question);
        flashcard.setAnswer(answer);
        flashcard.setId(id);
        return flashcard;
    }

    public static RichTextBlock createRichTextBlock(String data, long id) {
        return createRichTextBlock(data, id, 0);
    }

    public static RichTextBlock createRichTextBlock(String data, long id, long locationIndex) {
        RichTextBlock richTextBlock = new RichTextBlock();
        richTextBlock.setId(id);
        richTextBlock.setData(data);
        richTextBlock.setLocationIndex(locationIndex);
        return richTextBlock;
    }

    public static CodeBlock createCodeBlock(String data, long id) {
        CodeBlock codeBlock = new CodeBlock();
        codeBlock.setData(data);
        codeBlock.setId(id);
        return codeBlock;
    }

    public static FlashcardBlock createFlashcardBlock(long id, long locationIndex) {
        FlashcardBlock flashcardBlock = new FlashcardBlock();
        flashcardBlock.setId(id);
        flashcardBlock.setLocationIndex(locationIndex);
        flashcardBlock.setFlashcards(new ArrayList<>());
        return flashcardBlock;
    }

    public static boolean isBlocksSorted(NoteDTO noteDTO) {
        List<BlockDTO> blocks = noteDTO.getBlocks();

        for (int i = 1; i < blocks.size(); i++) {
            if (blocks.get(i-1).getLocationIndex() > blocks.get(i).getLocationIndex()) {
                return false;
            }
        }

        return true;
    }

    public static User getMockUser() {
        User user = new User();
        user.setId("firebase-id");
        return user;
    }
}
