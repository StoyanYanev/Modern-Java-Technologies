package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TextSplitter {
    private static final int INDEX_OF_AUTHOR_NAME = 0;
    private static final int INDEX_OF_VALUES_SIGNATURE = 1;

    private TextSplitter() {

    }

    public static String getAuthorName(String line) {
        if (line.isEmpty()) {
            return null;
        }

        return line.split(",")[INDEX_OF_AUTHOR_NAME];
    }

    public static LinguisticSignature getAuthorLinguisticSignature(String line) {
        if (line.isEmpty()) {
            return null;
        }

        Map<FeatureType, Double> features = new LinkedHashMap<>();
        String[] valuesOfSignature = line.split(",");
        int startIndexOfValuesSignature = INDEX_OF_VALUES_SIGNATURE;
        for (FeatureType type : FeatureType.values()) {
            features.put(type, Double.parseDouble(valuesOfSignature[startIndexOfValuesSignature]));
            startIndexOfValuesSignature++;
        }

        return new LinguisticSignature(features);
    }

    public static Map<String, Long> getAllWords(List<String> text) {
        List<String> allTokens = getAllTokens(text);
        Map<String, Long> allWordsInText = new HashMap<>();
        String currentWord;
        Long wordFrequency;
        for (String token : allTokens) {
            currentWord = cleanUp(token);
            if (currentWord.isEmpty()) {
                continue;
            }
            wordFrequency = allWordsInText.get(currentWord);
            if (wordFrequency == null) {
                wordFrequency = 0L;
            }
            allWordsInText.put(currentWord, wordFrequency + 1);
        }

        return allWordsInText;
    }

    public static List<String> getAllTokens(List<String> text) {
        List<String> tokens = new ArrayList<>();
        text.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .forEach(tokens::add);

        return tokens;
    }

    public static List<String> getAllSentences(List<String> text) {
        String[] sentences = text.stream()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.joining("\\n"))
                .split("[.?!]");

        return Arrays.stream(sentences)
                .filter(sentence -> !sentence.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+"
                        + "|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }
}