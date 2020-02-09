package com.glinevg.subanagram.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.Stream.builder;

public class AnagramUtil {

    public static Stream<String> readDictionaryAndSkipLineIfNextStartWithCurrent(Path dictionary) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(dictionary.toFile()))) {
            if (!scanner.hasNextLine()) {
                return Stream.empty();
            }
            Stream.Builder<String> sb = builder();

            String previous = scanner.next();
            while (scanner.hasNextLine()) {
                String current = scanner.nextLine();

                if (!current.startsWith(previous)) {
                    sb.add(previous);
                }
                previous = current;
            }

            return sb.build();
        }
    }

}
