package word.analyzer;

public class WordAnalyzer {
    private static final int NUMBER_OF_LETTERS = 26;

    public static String getSharedLetters(String word1, String word2) {
        int[] occurrencesFirst = new int[NUMBER_OF_LETTERS];
        int[] occurrencesSecond = new int[NUMBER_OF_LETTERS];

        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        for (int i = 0; i < word1.length(); i++) {
            occurrencesFirst[word1.charAt(i) - 'a']++;
        }

        for (int i = 0; i < word2.length(); i++) {
            occurrencesSecond[word2.charAt(i) - 'a']++;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < occurrencesFirst.length; i++) {
            if (occurrencesFirst[i] == 1 && occurrencesSecond[i] == 1) {
                result.append((char) (i + 'a'));
            }
        }

        return result.toString();
    }
}
