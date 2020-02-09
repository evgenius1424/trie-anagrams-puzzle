package com.glinevg.subanagram.model.trie;

import com.glinevg.subanagram.model.Word;
import lombok.Value;

import java.util.Map.Entry;
import java.util.Stack;

public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(Word word) {
        TrieNode current = root;
        for (Entry<Character, Integer> entry : word.characters()) {
            current = current.getOrCreateChildNode(TrieKey.from(entry));
        }
        current.add(word);
    }

    public boolean contains(Word word) {
        TrieNode current = root;

        for (Entry<Character, Integer> entry : word.characters()) {
            TrieNode node = current.getChildren().get(TrieKey.from(entry));
            if (node == null) {
                return false;
            }
            current = node;
        }

        return current.getWords().contains(word);
    }

    public boolean isAnagramOrSubAnagram(Word word) {
        Character minCharacter = word.getMinCharacter();

        Stack<TrieNode> stack = new Stack<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            TrieNode node = stack.pop();

            for (Entry<TrieKey, TrieNode> entry : node.getChildren().entrySet()) {
                char character = entry.getKey().getCharacter();
                if (character < minCharacter) {
                    stack.add(entry.getValue());
                } else if (character > minCharacter) {
                    break;
                } else if (entry.getKey().getCount() >= word.getCharacterCount(minCharacter)) {
                    if (doesMinWordCharacterNodeContainAnagram(entry, word)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean doesMinWordCharacterNodeContainAnagram(Entry<TrieKey, TrieNode> minWordCharacterNode, Word word) {

        if (word.characters().size() == 1) {
            return !isLastNodeAndContainsOnlyWord(minWordCharacterNode.getValue(), word);
        }

        Stack<NextNodeSearchData> stack = new Stack<>();
        stack.add(NextNodeSearchData.start(minWordCharacterNode, word));

        while (!stack.isEmpty()) {
            NextNodeSearchData searcher = stack.pop();
            for (Entry<TrieKey, TrieNode> entry : searcher.getNode().getValue().getChildren().entrySet()) {
                char character = entry.getKey().getCharacter();
                if (character < searcher.character) {
                    stack.add(searcher.sameCharacter(entry));
                } else if (character > searcher.character) {
                    break;
                } else if (entry.getKey().getCount() >= searcher.count) {
                    if (searcher.character.equals(word.getMaxCharacter())) {

                        if (!isLastNodeAndContainsOnlyWord(entry.getValue(), word)) {
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

    private static boolean isLastNodeAndContainsOnlyWord(TrieNode node, Word word) {
        return node.getChildren().size() == 0
                && node.getWords().size() == 1
                && node.getWords().contains(word);
    }

    @Value
    private static class NextNodeSearchData {
        Entry<TrieKey, TrieNode> node;
        Word word;
        Character character;
        Integer count;

        private static NextNodeSearchData start(Entry<TrieKey, TrieNode> node, Word word) {
            Character nextCharacter = word.getNextCharacter(node.getKey().getCharacter());
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
}
