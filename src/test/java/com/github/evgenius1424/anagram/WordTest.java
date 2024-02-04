package com.github.evgenius1424.anagram;

import com.github.evgenius1424.anagram.model.Word;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;

import static org.assertj.core.api.Assertions.assertThat;

public class WordTest {

    @Test
    public void getCharactersCountSortedByCharacterAsc() {
        Word word = new Word("zoo");

        assertThat(word.characters()).containsExactly(
                new SimpleEntry<>('o', 2),
                new SimpleEntry<>('z', 1)
        );
    }

}
