package com.glinevg.subanagram.model;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;

import static org.assertj.core.api.Assertions.assertThat;

class WordTest {

    @Test
    void getCharactersCountSortedByCharacterAsc() {
        Word word = new Word("zoo");

        assertThat(word.characters()).containsExactly(
                new SimpleEntry<>('o', 2),
                new SimpleEntry<>('z', 1)
        );
    }

}
