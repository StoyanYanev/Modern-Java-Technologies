package bg.sofia.uni.fmi.mjt.jira.utils;

public final class Utils {
    private Utils() {

    }

    public static void checkIfObjectIsNull(Object obj) {
        if (obj == null) {
            throw new RuntimeException("The object can not be null!");
        }
    }

    public static void checkIfStringIsValid(String str) {
        if (str == null) {
            throw new RuntimeException("The field can not be null!");
        } else if (str.isEmpty()) {
            throw new RuntimeException("The field can not be empty!");
        }
    }
}
