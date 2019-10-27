package funnel.checker;

public class FunnelChecker {
    private static final int MAX_DISTANCE_BETWEEN_SYMBOLS = 2;
    private static final int MAX_DIFFERENT_SYMBOLS = 2;

    public static boolean isFunnel(String str1, String str2) {
        if (str1.length() - 1 > str2.length() || str1.length() < str2.length()) {
            return false;
        }

        int differentSymbols = 0;
        int startIndex = 0;
        int indexOfCloserSymbol = 0;
        for (int i = 0; i < str1.length(); i++) {
            indexOfCloserSymbol = str2.indexOf(str1.charAt(i), startIndex);
            if (indexOfCloserSymbol == -1 || Math.abs(i - indexOfCloserSymbol) >= MAX_DISTANCE_BETWEEN_SYMBOLS) {
                differentSymbols++;
            } else {
                startIndex = i;
            }
            if (differentSymbols == MAX_DIFFERENT_SYMBOLS) {
                return false;
            }
        }
        return true;
    }
}
