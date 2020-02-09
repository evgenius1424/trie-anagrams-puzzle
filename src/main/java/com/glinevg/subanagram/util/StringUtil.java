package com.glinevg.subanagram.util;

import java.util.HashSet;
import java.util.Set;

public class StringUtil {

    public static Set<String> substringsPermutations(String string) {
        Set<String> set = new HashSet<>();
        char[] values = string.toCharArray();
        for (int width = 1; width <= values.length; width++) {
            int[] stack = new int[width];
            for (int i = 0; i < stack.length; i++) {
                stack[i] = stack.length - i - 1;
            }
            int position = 0;
            while (position < width) {

                position = 0;
                StringBuilder sb = new StringBuilder();
                while (position < width) {
                    sb.append(values[stack[position]]);
                    position++;
                }
                set.add(sb.toString());
                position = 0;
                while (position < width) {
                    if (stack[position] < values.length - 1) {
                        stack[position]++;
                        if (!containsDuplicate(stack))
                            break;
                        else
                            position = 0;
                    } else {
                        stack[position] = 0;
                        position++;
                    }
                }
            }
        }
        return set;
    }

    private static boolean containsDuplicate(int[] stack) {
        for (int i = 0; i < stack.length; i++) {
            for (int j = 0; j < stack.length; j++) {
                if (stack[i] == stack[j] && i != j) {
                    return true;
                }
            }
        }
        return false;
    }
}
