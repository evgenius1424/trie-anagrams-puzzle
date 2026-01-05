package com.github.evgenius1424.anagram;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        TrieAnagramsRemoval englishDictionary = new TrieAnagramsRemoval(getDictionary("english-dictionary.txt"));
        long start = System.currentTimeMillis();
        var notAnagramsEnglish = englishDictionary.getNotAnagrams();
        System.out.printf("English not-anagrams %d. Elapsed: %dms.%n", notAnagramsEnglish.size(), System.currentTimeMillis() - start);
    }

    private static Set<String> getDictionary(String fileName) throws URISyntaxException, IOException {
        return getDictionary(fileName, StandardCharsets.UTF_8);
    }

    private static Set<String> getDictionary(String fileName, Charset charset) throws URISyntaxException, IOException {
        URI uri = requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).toURI();
        try (var lines = Files.lines(Paths.get(uri), charset)) {
            return lines.collect(Collectors.toSet());
        }
    }
}
