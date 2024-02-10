package com.github.evgenius1424.anagram;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

@Disabled
public class PerformanceTest {

    @Test
    public void test() {
        Set<Word> words = IntStream.range(0, 100000).mapToObj(i -> RandomStringUtils.randomAlphabetic(10).toLowerCase())
                .map(Word::new)
                .collect(Collectors.toSet());
        System.out.println("Test with " + words.size() + " words");
        Trie trie = new Trie();
        words.forEach(trie::insert);
        long start = System.currentTimeMillis();
        Set<Word> result = words.stream().filter(not(trie::isAnagramOrSubAnagram)).collect(Collectors.toSet());
        long end = System.currentTimeMillis();
        System.out.println("Elapsed: " + (end - start) + ". Not anagrams:" + result.size());
    }
}
