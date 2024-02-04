import com.github.evgenius1424.anagram.model.Word;
import com.github.evgenius1424.anagram.model.trie.Trie;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static com.github.evgenius1424.anagram.AnagramUtil.readDictionaryAndSkipLineIfNextStartWithCurrent;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class Main {

    public static final String ENGLISH_DICTIONARY_RESOURCE = "english-dictionary.txt";

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path dictionary = resource(ENGLISH_DICTIONARY_RESOURCE);
        SortedMap<String, Set<String>> wordsGroupedBySortedLetters = readDictionaryAndSkipLineIfNextStartWithCurrent(dictionary)
                .collect(groupingBy(s -> s.chars()
                        .sorted()
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString(), TreeMap::new, toSet()));

        Trie trie = new Trie();
        List<Word> words = removedSubAnagramsBasedOnNaturalOrderOfSortedLetters(wordsGroupedBySortedLetters)
                .map(Word::new)
                .peek(trie::insert)
                .toList();

        long start = System.currentTimeMillis();
        long notAnagrams = words.parallelStream()
                .filter(not(trie::isAnagramOrSubAnagram))
                .count();
        System.out.println("Not anagrams" + notAnagrams + " Elapsed: " + (System.currentTimeMillis() - start) + "ms.");
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

    private static Path resource(String name) throws URISyntaxException {
        URI uri = requireNonNull(Thread.currentThread().getContextClassLoader().getResource(name)).toURI();
        return Paths.get(uri);
    }
}
