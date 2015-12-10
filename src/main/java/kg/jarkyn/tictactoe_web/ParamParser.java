package kg.jarkyn.tictactoe_web;

import kg.jarkyn.GameOption;
import kg.jarkyn.Mark;

public class ParamParser {
    public static int parseNumeric(String numeric) throws NumberFormatException {
        return Integer.parseInt(numeric);
    }

    public static GameOption parseGameOption(String numericGameOption) {
        return GameOption.parse(parseNumeric(numericGameOption));
    }

    public static Mark[] parseMoves(String moves) {
        String[] splitMoves = moves.split("-");
        Mark[] marks = new Mark[splitMoves.length];

        for (int i = 0; i < splitMoves.length; i++) {
            marks[i] = Mark.toMark(splitMoves[i]);
        }

        return marks;
    }
}
