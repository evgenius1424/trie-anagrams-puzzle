package com.github.evgenius1424.anagram.helper;

import java.util.HashSet;
import java.util.Set;

public class PermutationsHelper {

    public static Set<String> substringsPermutations(String string) {
        Set<String> result = new HashSet<>();
        generatePermutations("", string, result);
        return result;
    }

    private static void generatePermutations(String prefix, String remaining, Set<String> result) {
        int n = remaining.length();
        if (n == 0) {
            result.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                generatePermutations(prefix + remaining.charAt(i),
                        remaining.substring(0, i) + remaining.substring(i + 1, n),
                        result);
            }
        }
    }
}
