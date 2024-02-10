package com.github.evgenius1424.anagram;

import java.util.*;
import java.util.Map.Entry;

import static java.util.Comparator.comparing;

public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(Word word) {
        TrieNode current = root;
        for (Entry<Character, Integer> entry : word.characters()) {
            TrieKey key = TrieKey.from(entry);
            current = current.children.computeIfAbsent(key, v -> new TrieNode());
        }
        current.words.add(word);
    }

    public boolean contains(Word word) {
        TrieNode current = root;

        for (Entry<Character, Integer> entry : word.characters()) {
            TrieNode node = current.children.get(TrieKey.from(entry));
            if (node == null) {
                return false;
            }
            current = node;
        }

        return current.words.contains(word);
    }

    public boolean isAnagramOrSubAnagram(Word word) {
        Character minCharacter = word.getMinCharacter();

        Deque<TrieNode> stack = new LinkedList<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            TrieNode node = stack.pop();

            for (Entry<TrieKey, TrieNode> entry : node.children.entrySet()) {
                char character = entry.getKey().character();
                if (character < minCharacter) {
                    stack.add(entry.getValue());
                } else if (character > minCharacter) {
                    break;
                } else if (entry.getKey().count() >= word.getCharacterCount(minCharacter)) {
                    if (doesMinWordCharacterNodeContainAnagram(entry, word)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean doesMinWordCharacterNodeContainAnagram(Entry<TrieKey, TrieNode> minWordCharacterNode, Word word) {
        if (word.characters().size() == 1) {
            return isLastNodeAndContainsOnlyWord(minWordCharacterNode.getValue(), word);
        }

        Stack<NextNodeSearchData> stack = new Stack<>();
        stack.add(NextNodeSearchData.start(minWordCharacterNode, word));

        while (!stack.isEmpty()) {
            NextNodeSearchData searcher = stack.pop();
            for (Entry<TrieKey, TrieNode> entry : searcher.node.getValue().children.entrySet()) {
                char character = entry.getKey().character();
                if (character < searcher.character) {
                    stack.add(searcher.sameCharacter(entry));
                } else if (character > searcher.character) {
                    break;
                } else if (entry.getKey().count() >= searcher.count) {
                    if (searcher.character.equals(word.getMaxCharacter())) {

                        if (isLastNodeAndContainsOnlyWord(entry.getValue(), word)) {
                            return true;
                        }

                    } else {
                        stack.add(searcher.nextCharacter(entry));
                    }
                }
            }
        }

        return false;
    }

    private boolean isLastNodeAndContainsOnlyWord(TrieNode node, Word word) {
        return !node.children.isEmpty()
                || node.words.size() != 1
                || !node.words.contains(word);
    }

    private record NextNodeSearchData(Entry<TrieKey, TrieNode> node, Word word, Character character, Integer count) {
        private static NextNodeSearchData start(Entry<TrieKey, TrieNode> node, Word word) {
            Character nextCharacter = word.getNextCharacter(node.getKey().character());
            Integer nextCharacterCount = word.getCharacterCount(nextCharacter);
            return new NextNodeSearchData(node, word, nextCharacter, nextCharacterCount);
        }

        private NextNodeSearchData sameCharacter(Entry<TrieKey, TrieNode> node) {
            return new NextNodeSearchData(node, word, character, count);
        }

        private NextNodeSearchData nextCharacter(Entry<TrieKey, TrieNode> node) {
            Character nextCharacter = word.getNextCharacter(character);
            Integer nextCharacterCount = word.getCharacterCount(nextCharacter);
            return new NextNodeSearchData(node, word, nextCharacter, nextCharacterCount);
        }
    }

    private static class TrieNode {

        private final SortedMap<TrieKey, TrieNode> children = new TreeMap<>(comparing(TrieKey::character)
                .thenComparing(TrieKey::count));

        private final Set<Word> words = new HashSet<>();
    }

    private record TrieKey(char character, int count) {
        public static TrieKey from(Entry<Character, Integer> entry) {
            return new TrieKey(entry.getKey(), entry.getValue());
        }
    }
}
