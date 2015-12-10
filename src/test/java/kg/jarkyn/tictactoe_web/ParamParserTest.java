package kg.jarkyn.tictactoe_web;

import kg.jarkyn.GameOption;
import kg.jarkyn.Mark;
import org.junit.Test;

import static kg.jarkyn.Mark.NONE;
import static kg.jarkyn.Mark.X;
import static org.junit.Assert.assertArrayEquals;
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

    @Test
    public void parsesMoves() {
        Mark[] expectedMoves = new Mark[]{X, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE};
        assertArrayEquals(expectedMoves, ParamParser.parseMoves("X-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE"));
    }
}
