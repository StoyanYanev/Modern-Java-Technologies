package bg.sofia.uni.fmi.mjt.authorship.detection;

import org.junit.BeforeClass;
import org.junit.Test;
import utils.DataUtils;
import utils.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TextSplitterTest {
    private static final String SIGNATURE = "Alexandre Dumas, 3.7, 0.67, 0.53, 40.33, 5.0";

    private static List<String> text;

    @BeforeClass
    public static void initialize() throws IOException {
        InputStream input = new ByteArrayInputStream(DataUtils.DATA.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        text = reader.lines().collect(Collectors.toList());
        reader.close();
    }

    @Test
    public void testGetAuthorNameShouldReturnValidAuthorName() {
        final String expectedAuthorName = "Alexandre Dumas";
        assertEquals(expectedAuthorName, TextSplitter.getAuthorName(SIGNATURE));
    }

    @Test
    public void testGetAuthorLinguisticSignatureShouldReturnValidLinguisticSignature() {
        assertEquals(Utils.FIRST_SIGNATURE, TextSplitter.getAuthorLinguisticSignature(SIGNATURE));
    }

    @Test
    public void testGetAllWordsShouldReturnValidNumberOfWords() {
        final int expectedNumberOfWords = 121;
        final long actualNumberOfWords = TextSplitter.getAllWords(text)
                .values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        assertEquals(expectedNumberOfWords, actualNumberOfWords);
    }

    @Test
    public void testGetAllTokensShouldReturnValidNumberOfTokens() {
        final int expectedNumberOfTokens = 132;
        assertEquals(expectedNumberOfTokens, TextSplitter.getAllTokens(text).size());
    }

    @Test
    public void testGetAllSentencesShouldReturnValidNumberOfSentences() {
        final int expectedNumberOfSentences = 3;
        assertEquals(expectedNumberOfSentences, TextSplitter.getAllSentences(text).size());
    }

}