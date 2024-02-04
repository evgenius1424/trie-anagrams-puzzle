# Anagram and Subanagram Eliminator 🚫

## Problem Statement

The challenge is to determine the number of words that remain in the Russian/English (or any other language) language dictionary after removing all anagrams and subanagrams. 
The russian dictionary contains over 1.5 million words in various forms.

## Approach

The proposed solution involves the use of a Trie data structure to efficiently search for anagrams and subanagrams in the dictionary. 
The Trie is constructed by adding each word to the tree, where each node contains a letter and its count in the word. Nodes are sorted alphabetically and by letter frequency in the word.

Algorithm (simplified):

1. Add the dictionary to a Trie data structure.
2. For each word in the dictionary, search for nodes starting with the minimum letter of the word.
3. Once a matching node is found, traverse the subtree to check if the remaining letters exist in the required quantity.
4. Advantage of Trie: Quick exit from the subtree if the node's letter is greater than the searched letter.

## Example

Consider a dictionary with five tricky words: ракета, карета, арка, кот, мокрота.

1. Add the words to the Trie.
2. For the word "кот," search for nodes starting with "к" in the Trie.
3. Find nodes with "о-2" and "т-1" below the "к-1" node, satisfying the conditions for the word "кот."

## How to Run

Execute the following command to print all words remaining in the dictionary after eliminating anagrams and subanagrams:

```shell
./gradlew run
