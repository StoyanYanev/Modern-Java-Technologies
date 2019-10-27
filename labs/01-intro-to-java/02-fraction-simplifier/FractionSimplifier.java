package fraction.simplifier;

public class FractionSimplifier {
    private static final int POSITION_OF_FIRST_ELEMENT = 0;
    private static final int POSITION_OF_SECOND_ELEMENT = 1;

    public static String simplify(String fraction) {
        String[] slittedBySlash = fraction.split("/");
        int numerator = Integer.parseInt(slittedBySlash[POSITION_OF_FIRST_ELEMENT]);
        int denominator = Integer.parseInt(slittedBySlash[POSITION_OF_SECOND_ELEMENT]);
        int divisor = getGreatestCommonDivisor(numerator, denominator);

        if (divisor == 1 && denominator == 1) {
            return String.valueOf(numerator);
        } else if (divisor == 1) {
            return fraction;
        } else {
            numerator /= divisor;
            denominator /= divisor;
        }

        if (denominator == 1) {
            return String.valueOf(numerator);
        }

        return numerator + "/" + denominator;
    }

    private static int getGreatestCommonDivisor(int firstNumber, int secondNumber) {
        if (secondNumber == 0) {
            return firstNumber;
        }
        return getGreatestCommonDivisor(secondNumber, firstNumber % secondNumber);
    }
}
