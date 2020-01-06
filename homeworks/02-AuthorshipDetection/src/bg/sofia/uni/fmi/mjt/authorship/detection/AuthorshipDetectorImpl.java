package bg.sofia.uni.fmi.mjt.authorship.detection;

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

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        authorLinguisticSignature = new HashMap<>();
        buildAuthorsLinguisticSignature(signaturesDataset);
        this.weights = weights;
    }

    public Map<String, LinguisticSignature> getAuthorLinguisticSignature() {
        return authorLinguisticSignature;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        validateText(mysteryText);

        LinguisticSignature linguisticSignature = calculateSignature(mysteryText);
        Map<String, Double> authorSimilarity = new HashMap<>();
        double currentSimilarity;
        for (Map.Entry<String, LinguisticSignature> currentAuthorLinguisticSignature :
                authorLinguisticSignature.entrySet()) {
            currentSimilarity = calculateSimilarity(currentAuthorLinguisticSignature.getValue(),
                    linguisticSignature);
            authorSimilarity.put(currentAuthorLinguisticSignature.getKey(), currentSimilarity);
        }

        return authorSimilarity.entrySet()
                .stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        validateText(mysteryText);
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

    private void buildAuthorsLinguisticSignature(InputStream signaturesDataset) {
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
            e.printStackTrace();
        }
    }

    private void validateText(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateLinguisticSignature(LinguisticSignature signature) {
        if (signature == null) {
            throw new IllegalArgumentException();
        }
    }
}