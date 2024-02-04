package com.github.evgenius1424.anagram.model.trie;

import com.github.evgenius1424.anagram.model.Word;
import lombok.Getter;

import java.util.*;

import static java.util.Comparator.comparing;

@Getter
class TrieNode {

    private static final Comparator<TrieKey> TRIE_KEY_COMPARATOR = comparing(TrieKey::character)
            .thenComparing(TrieKey::count);

    private final SortedMap<TrieKey, TrieNode> children = new TreeMap<>(TRIE_KEY_COMPARATOR);

    private final Set<Word> words = new HashSet<>();

    boolean add(Word word) {
        return words.add(word);
    }

    TrieNode getOrCreateChildNode(TrieKey key) {
        return children.computeIfAbsent(key, v -> new TrieNode());
    }
}
