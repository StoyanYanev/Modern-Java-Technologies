package bg.sofia.uni.fmi.mjt.jira.generator;

public final class IdGenerator {
    private static int id = 0;

    private IdGenerator() {
    }

    public static int generate() {
        return id++;
    }
}
