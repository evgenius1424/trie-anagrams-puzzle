package com.github.evgenius1424.anagram.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Word {

    @ToString.Include
    @Getter
    @EqualsAndHashCode.Include
    private final String source;
    private final SortedMap<Character, Integer> charactersCount;

    public Word(String source) {
        this.source = source;
        this.charactersCount = charactersCount(source);
    }

    public Character getNextCharacter(Character character) {
        return charactersCount.keySet().stream().filter(ch -> ch > character)
                .findFirst()
                .orElseThrow();
    }

    public Character getMinCharacter() {
        return charactersCount.firstKey();
    }

    public Character getMaxCharacter() {
        return charactersCount.lastKey();
    }

    public Integer getCharacterCount(Character character) {
        return charactersCount.get(character);
    }

    public Set<Entry<Character, Integer>> characters() {
        return charactersCount.entrySet();
    }

    private SortedMap<Character, Integer> charactersCount(String word) {
        SortedMap<Character, Integer> map = new TreeMap<>();

        for (int i = 0; i < word.length(); i++) {
            map.compute(word.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }

        return map;
    }

}
