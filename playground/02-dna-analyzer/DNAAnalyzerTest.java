package playground.dna.analyzer;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DNAAnalyzerTest {

    private static DNAAnalyzer dnaAnalyzer;

    @BeforeClass
    public static void setUp() {
        dnaAnalyzer = new DNAAnalyzer();
    }

    @Test
    public void testDNAAnalyzer_Abracadabra() {
        assertEquals("abra", dnaAnalyzer.longestRepeatingSequence("abracadabra"));
    }

    @Test
    public void testDNAAnalyzer_DNASequence() {
        assertEquals("TACTC", dnaAnalyzer.longestRepeatingSequence("ATACTCGGTACTCT"));
    }

}