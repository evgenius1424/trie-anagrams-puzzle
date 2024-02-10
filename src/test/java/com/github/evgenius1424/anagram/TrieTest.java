package com.github.evgenius1424.anagram;

import com.github.evgenius1424.anagram.helper.PermutationsHelper;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrieTest {

    @Test
    public void wordsContainedInTrie() {
        Trie trie = new Trie();

        List<Word> words = Stream.of("dog", "dogma", "mississippi", "z")
                .map(Word::new)
                .peek(trie::insert)
                .toList();

        words.forEach(w -> assertTrue(trie.contains(w)));
        assertThat(trie.contains(new Word("missing"))).isFalse();
    }

    @Test
    public void isAnagram() {
        Word a1 = new Word("abc");
        Word a2 = new Word("cba");

        Trie trie = new Trie();

        trie.insert(a1);
        trie.insert(a2);

        assertThat(trie.isAnagramOrSubAnagram(a1)).isTrue();
        assertThat(trie.isAnagramOrSubAnagram(a2)).isTrue();
    }

    @Test
    public void isSubAnagram() {
        Word parent = new Word("english");
        Word subAnagram = new Word("single");

        Trie trie = new Trie();

        trie.insert(parent);
        trie.insert(subAnagram);

        assertThat(trie.isAnagramOrSubAnagram(parent)).isFalse();
        assertThat(trie.isAnagramOrSubAnagram(subAnagram)).isTrue();
    }

    @Test
    public void substringsPermutationsAreAnagramsAndSubAnagrams() {
        Trie trie = new Trie();

        List<Word> anagramsAndSubAnagrams = Stream.of("peace", "door")
                .map(PermutationsHelper::substringsPermutations)
                .flatMap(Collection::stream)
                .map(Word::new)
                .peek(trie::insert)
                .toList();

        Word notSubAnagram = new Word("ball");
        trie.insert(notSubAnagram);

        assertThat(anagramsAndSubAnagrams.stream()
                .filter(not(trie::isAnagramOrSubAnagram))
                .collect(toList())).isEmpty();

        assertThat(trie.isAnagramOrSubAnagram(notSubAnagram)).isFalse();
    }
}
