package com.glinevg.subanagram.model.trie;

import com.glinevg.subanagram.model.Word;
import com.glinevg.subanagram.util.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrieTest {

    @Test
    void wordsContainedInTrie() {
        Trie trie = new Trie();

        List<Word> words = Stream.of("dog", "dogma", "mississippi", "z")
                .map(Word::new)
                .peek(trie::insert)
                .collect(toList());

        words.forEach(w -> assertTrue(trie.contains(w)));
        assertThat(trie.contains(new Word("missing"))).isFalse();
    }

    @Test
    void isSubAnagram() {
        Word parent = new Word("english");
        Word subAnagram = new Word("single");

        Trie trie = new Trie();

        trie.insert(parent);
        trie.insert(subAnagram);

        assertThat(trie.isAnagramOrSubAnagram(parent)).isFalse();
        assertThat(trie.isAnagramOrSubAnagram(subAnagram)).isTrue();
    }

    @Test
    void isAnagram() {
        Word a1 = new Word("abc");
        Word a2 = new Word("cba");

        Trie trie = new Trie();

        trie.insert(a1);
        trie.insert(a2);

        assertThat(trie.isAnagramOrSubAnagram(a1)).isTrue();
        assertThat(trie.isAnagramOrSubAnagram(a2)).isTrue();
    }

    @Test
    void substringsPermutationsAreAnagramsAndSubAnagrams() {
        Trie trie = new Trie();

        List<Word> anagramsAndSubAnagrams = Stream.of("peace", "door")
                .map(StringUtil::substringsPermutations)
                .flatMap(Collection::stream)
                .map(Word::new)
                .peek(trie::insert)
                .collect(toList());

        Word notSubAnagram = new Word("ball");
        trie.insert(notSubAnagram);

        assertThat(anagramsAndSubAnagrams.stream()
                .filter(not(trie::isAnagramOrSubAnagram))
                .collect(toList())).isEmpty();

        assertThat(trie.isAnagramOrSubAnagram(notSubAnagram)).isFalse();
    }
}
