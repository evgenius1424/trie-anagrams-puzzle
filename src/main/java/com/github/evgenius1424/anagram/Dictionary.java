package com.github.evgenius1424.anagram;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@RequiredArgsConstructor
public class Dictionary {

    private final Set<String> words;

    public Set<String> getNotAnagrams() {
        Trie trie = new Trie();

        List<Word> wordsInTrie = words.stream()
                .map(Word::new)
                .peek(trie::insert)
                .toList();

        return wordsInTrie.parallelStream()
                .filter(not(trie::isAnagramOrSubAnagram))
                .map(Word::getSource)
                .collect(Collectors.toSet());
    }
}
