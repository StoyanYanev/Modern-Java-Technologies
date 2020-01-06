package utils;

import bg.sofia.uni.fmi.mjt.authorship.detection.FeatureType;
import bg.sofia.uni.fmi.mjt.authorship.detection.LinguisticSignature;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Utils {
    public static final double[] WEIGHTS = new double[]{11, 33, 50, 0.4, 4};
    public static final LinguisticSignature FIRST_SIGNATURE = new LinguisticSignature(initFirstFeaturesMap());
    public static final LinguisticSignature SECOND_SIGNATURE = new LinguisticSignature(initSecondFeaturesMap());

    private Utils() {

    }

    private static Map<FeatureType, Double> initFirstFeaturesMap() {
        final double averageWordLength = 3.70;
        final double typeTokenRatio = 0.67;
        final double hapaxLegomenaRatio = 0.53;
        final double averageSentenceLength = 40.33;
        final double averageSentenceComplexity = 5.0;

        return buildFeaturesMap(averageWordLength, typeTokenRatio, hapaxLegomenaRatio,
                averageSentenceLength, averageSentenceComplexity);
    }

    private static Map<FeatureType, Double> initSecondFeaturesMap() {
        final double averageWordLength = 4.3;
        final double typeTokenRatio = 0.1;
        final double hapaxLegomenaRatio = 0.04;
        final double averageSentenceLength = 16.0;
        final double averageSentenceComplexity = 4.0;

        return buildFeaturesMap(averageWordLength, typeTokenRatio, hapaxLegomenaRatio,
                averageSentenceLength, averageSentenceComplexity);
    }

    private static Map<FeatureType, Double> buildFeaturesMap(double averageWordLength, double typeTokenRatio,
                                                             double hapaxLegomenaRatio, double averageSentenceLength,
                                                             double averageSentenceComplexity) {

        final Map<FeatureType, Double> features = new LinkedHashMap<>();
        features.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLength);
        features.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRatio);
        features.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatio);
        features.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLength);
        features.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexity);

        return Collections.unmodifiableMap(features);
    }
}