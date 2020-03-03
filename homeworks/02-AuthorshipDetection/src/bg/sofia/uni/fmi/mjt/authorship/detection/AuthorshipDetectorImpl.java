package bg.sofia.uni.fmi.mjt.authorship.detection;

import bg.sofia.uni.fmi.mjt.authorship.detection.exceptions.FailedToLoadSignaturesException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    private Map<String, LinguisticSignature> authorLinguisticSignature;
    private double[] weights;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights)
            throws FailedToLoadSignaturesException {
        authorLinguisticSignature = new HashMap<>();
        buildAuthorsLinguisticSignature(signaturesDataset);
        this.weights = weights;
    }

    public Map<String, LinguisticSignature> getAuthorLinguisticSignature() {
        return authorLinguisticSignature;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("The stream can not be null!");
        }

        LinguisticSignature linguisticSignature = calculateSignature(mysteryText);
        String author = null;
        double bestSimilarity = Double.MAX_VALUE;
        for (Map.Entry<String, LinguisticSignature> currentAuthorLinguisticSignature :
                authorLinguisticSignature.entrySet()) {
            double currentSimilarity = calculateSimilarity(currentAuthorLinguisticSignature.getValue(),
                    linguisticSignature);

            if (currentSimilarity < bestSimilarity) {
                bestSimilarity = currentSimilarity;
                author = currentAuthorLinguisticSignature.getKey();
            }
        }

        return author;
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("The stream can not be null!");
        }
        Map<FeatureType, Double> features = FeaturesCalculator.buildAllFeatures(mysteryText);

        return new LinguisticSignature(features);
    }

    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature,
                                      LinguisticSignature secondSignature) {
        validateLinguisticSignature(firstSignature);
        validateLinguisticSignature(secondSignature);

        Map<FeatureType, Double> firstSignatureFeatures = firstSignature.getFeatures();
        Map<FeatureType, Double> secondSignatureFeatures = secondSignature.getFeatures();
        double sumOfSimilarityValue = 0;
        int index = 0;
        for (FeatureType currentFeature : FeatureType.values()) {
            sumOfSimilarityValue += Math.abs(firstSignatureFeatures.get(currentFeature)
                    - secondSignatureFeatures.get(currentFeature)) * weights[index];
            index++;
        }

        return sumOfSimilarityValue;
    }

    private void buildAuthorsLinguisticSignature(InputStream signaturesDataset) throws FailedToLoadSignaturesException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(signaturesDataset))) {
            String currentLine;
            String currentAuthorName;
            LinguisticSignature currentLinguisticSignature;
            while ((currentLine = reader.readLine()) != null) {
                currentAuthorName = TextSplitter.getAuthorName(currentLine);
                currentLinguisticSignature = TextSplitter
                        .getAuthorLinguisticSignature(currentLine);

                if (currentAuthorName != null && currentLinguisticSignature != null) {
                    authorLinguisticSignature.put(currentAuthorName, currentLinguisticSignature);
                }
            }
        } catch (IOException e) {
            throw new FailedToLoadSignaturesException("Can not load author signatures!", e.getCause());
        }
    }

    private void validateLinguisticSignature(LinguisticSignature signature) {
        if (signature == null) {
            throw new IllegalArgumentException("The linguistic signature can not be null!");
        }
    }
}