package bg.sofia.uni.fmi.mjt.authorship.detection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.DataUtils;
import utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorshipDetectorImplTest {
    private static final double DELTA = 0.01;

    private static AuthorshipDetectorImpl authorshipDetector;
    private static InputStream input;

    @BeforeClass
    public static void initialize() {
        authorshipDetector = new AuthorshipDetectorImpl(new ByteArrayInputStream(DataUtils.SIGNATURES.getBytes()),
                Utils.WEIGHTS);
    }

    @Before
    public void setUp() {
        input = new ByteArrayInputStream(DataUtils.DATA.getBytes());
    }

    @After
    public void close() throws IOException {
        input.close();
    }

    @Test
    public void testGetAuthorLinguisticSignatureShouldReturnValidLinguisticSignature() {
        final int expectedAuthorsNames = 12;
        assertEquals(expectedAuthorsNames, authorshipDetector.getAuthorLinguisticSignature().size());
    }

    @Test
    public void testCalculateSignatureWithValidGivenDataShouldReturnValidSignature() {
        final List<Double> expectedValues = new ArrayList<>(Utils.FIRST_SIGNATURE.getFeatures().values());
        final LinguisticSignature linguisticSignature = authorshipDetector
                .calculateSignature(new ByteArrayInputStream(DataUtils.DATA.getBytes()));
        final List<Double> actualValues = new ArrayList<>(linguisticSignature.getFeatures().values());

        for (int i = 0; i < expectedValues.size(); i++) {
            assertEquals(expectedValues.get(i), actualValues.get(i), DELTA);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWithNullDataShouldThrowException() {
        authorshipDetector.calculateSignature(null);
    }

    @Test
    public void testCalculateSimilarityWithValidGivenDataShouldReturnValidSimilarity() {
        final double expectedSimilarityValue = 63.64;
        assertEquals(expectedSimilarityValue, authorshipDetector.calculateSimilarity(Utils.FIRST_SIGNATURE,
                Utils.SECOND_SIGNATURE), DELTA);
    }

    @Test
    public void testCalculateSimilarityWithTwoIdenticalSignaturesShouldReturnZero() {
        final double expectedSimilarityValue = 0.0;
        assertEquals(expectedSimilarityValue, authorshipDetector.calculateSimilarity(Utils.FIRST_SIGNATURE,
                Utils.FIRST_SIGNATURE), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWithNullDataShouldThrowException() {
        authorshipDetector.calculateSimilarity(null, null);
    }

    @Test
    public void testFindAuthorWithValidGivenDataShouldReturnValidAuthor() {
        final String expectedAuthorName = "Brothers Grim";
        assertEquals(expectedAuthorName, authorshipDetector.findAuthor(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWithNullDataShouldThrowException() {
        authorshipDetector.findAuthor(null);
    }
}