package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public final class FeaturesCalculator {
    private FeaturesCalculator() {

    }

    public static Map<FeatureType, Double> buildAllFeatures(InputStream mysteryText) {
        Map<FeatureType, Double> features = new LinkedHashMap<>();
        Queue<Double> featuresValues = getFeaturesValues(mysteryText);
        for (FeatureType type : FeatureType.values()) {
            features.put(type, featuresValues.poll());
        }

        return features;
    }

    private static Queue<Double> getFeaturesValues(InputStream mysteryText) {
        Queue<Double> featuresValues = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(mysteryText))) {
            List<String> text = reader.lines()
                    .collect(Collectors.toList());

            Map<String, Long> words = TextSplitter.getAllWords(text);
            List<String> sentences = TextSplitter.getAllSentences(text);
            long numberOfAllWords = getNumberOfAllWords(words);

            featuresValues = new ArrayDeque<>();
            featuresValues.add(getAverageWordsLength(words, numberOfAllWords));
            featuresValues.add(getTypeTokenRation(words, numberOfAllWords));
            featuresValues.add(getHapaxLegomenaRatio(words, numberOfAllWords));
            featuresValues.add(getAverageNumberOfWordsInSentence(sentences, numberOfAllWords));
            featuresValues.add(getAverageSentenceComplexity(sentences));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return featuresValues;
    }

    private static double getAverageWordsLength(Map<String, Long> words, long numberOfAllWords) {
        if (numberOfAllWords == 0) {
            return 0;
        }
        long numberOfAllSymbols = words.keySet()
                .stream()
                .mapToLong(String::length)
                .sum();

        return (double) numberOfAllSymbols / numberOfAllWords;
    }

    private static double getTypeTokenRation(Map<String, Long> words, long numberOfAllWords) {
        if (numberOfAllWords == 0) {
            return 0;
        }
        return (double) words.keySet().size() / numberOfAllWords;
    }

    private static double getHapaxLegomenaRatio(Map<String, Long> words, long numberOfAllWords) {
        if (numberOfAllWords == 0) {
            return 0;
        }
        double numberOfUniqueWords = words.values()
                .stream()
                .filter(i -> i == 1)
                .count();

        return numberOfUniqueWords / numberOfAllWords;
    }

    private static double getAverageNumberOfWordsInSentence(List<String> sentences,
                                                            long numberOfAllWords) {
        if (sentences.isEmpty()) {
            return 0;
        }

        return (double) numberOfAllWords / sentences.size();
    }

    private static double getAverageSentenceComplexity(List<String> sentences) {
        if (sentences.isEmpty()) {
            return 0;
        }

        long totalNumberOfPhrases = sentences.stream()
                .map(sentence -> sentence.split("[,:;]"))
                .filter(sentence -> sentence.length != 1)
                .flatMap(Arrays::stream)
                .filter(phrase -> !phrase.isEmpty())
                .count();

        return (double) totalNumberOfPhrases / sentences.size();
    }

    private static long getNumberOfAllWords(Map<String, Long> word) {
        return word.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}