package kg.jarkyn.tictactoe_web;

import kg.jarkyn.GameOption;

public class ParamParser {
    public static int parseNumeric(String numeric) throws NumberFormatException {
        return Integer.parseInt(numeric);
    }

    public static GameOption parseGameOption(String numericGameOption) {
        return GameOption.parse(parseNumeric(numericGameOption));
    }
}
