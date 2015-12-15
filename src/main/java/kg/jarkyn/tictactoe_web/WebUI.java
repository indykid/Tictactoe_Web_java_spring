package kg.jarkyn.tictactoe_web;

import kg.jarkyn.*;

import java.util.List;

public class WebUI implements HumanInput {
    private static final int NULL_MOVE = -1;
    private int humanMove = NULL_MOVE;
    private Game game;

    @Override
    public int getMove(List<Integer> list) {
        int move = humanMove;
        humanMove = NULL_MOVE;
        return move;
    }

    @Override
    public boolean hasHumanMove() {
        return humanMove != NULL_MOVE;
    }

    public void setHumanMove(int humanMove) {
        this.humanMove = humanMove;
    }

    public void setupGame(String numericGameOption) {
        GameOption gameOption = ParamParser.parseGameOption(numericGameOption);
        this.game = GameFactory.makeGame(new Board(), gameOption, this);
        this.game.play();
    }

    public Game getGame() {
        return game;
    }

    public void playGame() {
        game.play();
    }

    public boolean isGameOver() {
        return game.isOver();
    }

    public String[] getMarks() {
        Mark[] moves = game.getBoard().getMoves();
        String[] marks = new String[moves.length];
        for (int i = 0; i < moves.length; i++) {
            marks[i] = convertMark(moves[i]);
        }
        return marks;
    }

    public String convertMark(Mark mark) {
        return mark == Mark.NONE ? "" : mark.toString();
    }

    public String getWinner() {
        Mark mark = game.winnerMark();
        return mark == Mark.NONE ? "" : mark.toString();
    }
}
