package com.glinevg.subanagram.model.trie;

import lombok.Value;

import java.util.Map.Entry;

@Value
class TrieKey {
    char character;
    int count;

    static TrieKey from(Entry<Character, Integer> entry) {
        return new TrieKey(entry.getKey(), entry.getValue());
    }
}
