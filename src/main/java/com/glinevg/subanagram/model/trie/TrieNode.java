package com.glinevg.subanagram.model.trie;

import com.glinevg.subanagram.model.Word;
import lombok.Getter;

import java.util.*;

import static java.util.Comparator.comparing;

@Getter
class TrieNode {

    private static final Comparator<TrieKey> TRIE_KEY_COMPARATOR = comparing(TrieKey::getCharacter)
            .thenComparing(TrieKey::getCount);

    private final SortedMap<TrieKey, TrieNode> children = new TreeMap<>(TRIE_KEY_COMPARATOR);

    private final Set<Word> words = new HashSet<>();

    boolean add(Word word) {
        return words.add(word);
    }

    TrieNode getOrCreateChildNode(TrieKey key) {
        return children.computeIfAbsent(key, v -> new TrieNode());
    }
}
