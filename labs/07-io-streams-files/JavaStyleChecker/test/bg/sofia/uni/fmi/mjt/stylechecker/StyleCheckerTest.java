package bg.sofia.uni.fmi.mjt.stylechecker;

import bg.sofia.uni.fmi.mjt.stylechecker.utils.ViolationsUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

public class StyleCheckerTest {
    private static StyleChecker styleChecker;


    @BeforeClass
    public static void setUp() {
        styleChecker = new StyleChecker();
    }

    @Test
    public void testCheckStyleWithWildcardImportShouldInsertWildcardNotAllowedComment() {
        String wildcardImport = "import java.lang.*;";
        String actual = getResult(wildcardImport);

        assertEquals(ViolationsUtils.WILDCARDS_NOT_ALLOWED
                + System.lineSeparator() + wildcardImport, actual.trim());
    }

    @Test
    public void testCheckStyleWithValidImportShouldNotInsertWildcardNotAllowedComment() {
        String validImport = "java.io.Reader";
        String actual = getResult(validImport);

        assertEquals(validImport, actual.trim());
    }

    @Test
    public void testCheckStyleWithMultipleStatementsPerLineShouldInsertOneStatementPerLineComment() {
        String oneStatementsPerLine = "sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();";
        String actual = getResult(oneStatementsPerLine);

        assertEquals(ViolationsUtils.ONE_STATEMENT_PER_LINE
                + System.lineSeparator() + oneStatementsPerLine, actual.trim());
    }

    @Test
    public void testCheckStyleWithValidStatementsShouldNotInsertOneStatementPerLineComment() {
        String multipleStatements = "sayHello()\n;sayHello();\nsayHello();\nsayHello()";
        String actual = getResult(multipleStatements);

        assertEquals(multipleStatements, actual.trim());
    }


    @Test
    public void testCheckStyleWithOpenBracketsOnSameLineShouldInsertOpenBracketsOnSameLineComment() {
        String bracketNotTheSameLine = "{for(int i=0; i < 100; i++))";
        String actual = getResult(bracketNotTheSameLine);

        assertEquals(ViolationsUtils.OPEN_BRACKETS_ON_SAME_LINE
                + System.lineSeparator() + bracketNotTheSameLine, actual.trim());
    }

    @Test
    public void testCheckStyleWithValidOpenBracketsShouldNotInsertOpenBracketsOnSameLineComment() {
        String bracketOnTheSameLine = "public static void sayHello(){";
        String actual = getResult(bracketOnTheSameLine);

        assertEquals(bracketOnTheSameLine, actual.trim());
    }

    @Test
    public void testCheckStyleWithPackageUpperCaseNameShouldInsertInvalidPackageNameComment() {
        String packageUpperCaseName = "package bg.uni.SOFIA.fmi.mjt;";
        String actual = getResult(packageUpperCaseName);

        assertEquals(ViolationsUtils.INVALID_PACKAGE_NAME
                + System.lineSeparator() + packageUpperCaseName, actual.trim());
    }


    @Test
    public void testCheckStyleWithPackageUnderscoreNameShouldInsertInvalidPackageNameComment() {
        String packageUnderscoreName = "package bg.uni_sofia.fmi.mjt;";
        String actual = getResult(packageUnderscoreName);

        assertEquals(ViolationsUtils.INVALID_PACKAGE_NAME
                + System.lineSeparator() + packageUnderscoreName, actual.trim());
    }

    @Test
    public void testCheckStyleWithValidPackageNameShouldNotInsertInvalidPackageNameComment() {
        String validPackageName = "package bg.uni.sofia.fmi.mjt;";
        String actual = getResult(validPackageName);

        assertEquals(validPackageName, actual.trim());
    }

    @Test
    public void testCheckStyleWithExceededLengthShouldInsertLineLengthNotExceedComment() {
        String lengthExceededLine = "Hey, it's Hannah, Hannah Baker. That's right. Don't adjust your... " +
                "whatever device you're listening to this on. It's me, live and in stereo.;";
        String actual = getResult(lengthExceededLine);

        assertEquals(ViolationsUtils.LINE_LENGTH_NOT_EXCEED
                + System.lineSeparator() + lengthExceededLine, actual.trim());
    }

    @Test
    public void testCheckStyleWithNoExceededLengthShouldNotInsertLineLengthNotExceedComment() {
        String validLengthLine = "Hey, it's Hannah, Hannah Baker. That's right. Don't adjust your...";

        String actual = getResult(validLengthLine);

        assertEquals(validLengthLine, actual);

    }

    private String getResult(String text) {
        Reader reader = new StringReader(text);
        Writer writer = new StringWriter();

        styleChecker.checkStyle(reader, writer);

        return writer.toString().trim();
    }
}