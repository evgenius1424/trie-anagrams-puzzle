import com.glinevg.subanagram.model.Word;
import com.glinevg.subanagram.model.trie.Trie;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static com.glinevg.subanagram.util.AnagramUtil.readDictionaryAndSkipLineIfNextStartWithCurrent;
import static com.glinevg.subanagram.util.ResourceUtil.resource;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path dictionary = resource("english-dictionary.txt");
        SortedMap<String, Set<String>> wordsGroupedBySortedLetters = readDictionaryAndSkipLineIfNextStartWithCurrent(dictionary)
                .collect(groupingBy(s -> s.chars()
                        .sorted()
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString(), TreeMap::new, toSet()));

        Trie trie = new Trie();
        List<Word> words = removedSubAnagramsBasedOnNaturalOrderOfSortedLetters(wordsGroupedBySortedLetters)
                .map(Word::new)
                .peek(trie::insert)
                .collect(toList());

        long start = System.currentTimeMillis();
        long notAnagrams = words.parallelStream()
                .filter(not(trie::isAnagramOrSubAnagram))
                .count();
        long finish = System.currentTimeMillis() - start;
        System.out.println("Not anagrams #" + notAnagrams + " Elapsed: " + finish + "ms.");
    }

    private static Stream<String> removedSubAnagramsBasedOnNaturalOrderOfSortedLetters(SortedMap<String, Set<String>> words) {
        Stream.Builder<String> sb = Stream.builder();

        Iterator<String> it = words.keySet().iterator();

        String previous = it.hasNext() ? it.next() : "";
        while (it.hasNext()) {
            String current = it.next();

            if (!current.startsWith(previous)) {
                words.get(previous).forEach(sb::add);
            }
            previous = current;
        }

        return sb.build();
    }
}
