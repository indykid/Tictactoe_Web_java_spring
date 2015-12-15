package kg.jarkyn.tictactoe_web;

import kg.jarkyn.GameOption;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParamParserTest {
    @Test
    public void parsesIntoNumber() {
        assertEquals(1, ParamParser.parseNumeric("1"));
    }

    @Test(expected=NumberFormatException.class)
    public void throwsNumberFormatExceptionWhenGivenNonNumericString() {
        ParamParser.parseNumeric("a");
    }

    @Test
    public void parsesIntoGameOption() {
        String numericGameOptionAiFirst = "1";
        assertEquals(GameOption.AI_FIRST, ParamParser.parseGameOption(numericGameOptionAiFirst));
    }
}
