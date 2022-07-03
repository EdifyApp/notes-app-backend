package com.notes.api.converters;

import com.notes.api.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListOfBlocksConverterTest {

    private ListOfBlocksConverter converter;

    @BeforeEach
    public void setup() {
        converter = new ListOfBlocksConverter();
    }

    @Test
    public void testConvertListWithSingleBlockToJson() {
        RichTextBlock richTextBlock = new RichTextBlock();
        richTextBlock.setData("some test");
        List<Block> blockList = new ArrayList<>();
        blockList.add(richTextBlock);

        String json = converter.convertToDatabaseColumn(blockList);
        String expected = "[{\"type\":\"RichTextBlock\",\"data\":\"some test\"}]";
        System.out.println(expected);
        Assertions.assertEquals(expected, json);
    }

    @Test
    public void testConvertJsonToListWithSingleBlock() {
        String json = "[{\"type\":\"RichTextBlock\",\"data\":\"some test\"}]";

        List<Block> converted = converter.convertToEntityAttribute(json);
        Assertions.assertEquals(1, converted.size());

        RichTextBlock block = (RichTextBlock) converted.get(0);
        Assertions.assertEquals(BlockType.RichTextBlock, block.getType());
        Assertions.assertEquals("some date", block.getData());
    }

    @Test
    public void testConvertListWithFlashcardBlockToJson() {
        List<Block> blocks = new ArrayList<>();

        FlashcardBlock flashcardBlock = new FlashcardBlock();
        List<Flashcard> flashcards = new ArrayList<>();
        flashcardBlock.setData(flashcards);

        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion("what is the meaning of life?");
        flashcard.setAnswer("42");

        flashcardBlock.getData().add(flashcard);
        blocks.add(flashcardBlock);

        String json = converter.convertToDatabaseColumn(blocks);
        String expected = "[{\"type\":\"FlashcardBlock\",\"data\":[{\"question\":\"what is the meaning of life?\",\"answer\":\"42\"}]}]";

        Assertions.assertEquals(expected, json);
    }
}
