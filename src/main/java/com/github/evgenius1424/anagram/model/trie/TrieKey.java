package com.github.evgenius1424.anagram.model.trie;

import java.util.Map.Entry;

public record TrieKey(char character, int count) {
    public static TrieKey from(Entry<Character, Integer> entry) {
        return new TrieKey(entry.getKey(), entry.getValue());
    }
}
