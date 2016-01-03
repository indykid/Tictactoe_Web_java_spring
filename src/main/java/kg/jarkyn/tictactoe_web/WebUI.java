package kg.jarkyn.tictactoe_web;

import kg.jarkyn.*;

import java.util.ArrayList;
import java.util.List;

public class WebUI implements HumanInput {
    private static final int NULL_MOVE = -1;
    private int humanMove = NULL_MOVE;
    private Game game;
    private GameOption gameOption;

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
        gameOption = ParamParser.parseGameOption(numericGameOption);
        game = GameFactory.makeGame(new Board(), gameOption, this);
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

    public List<String> formatMarks() {
        Mark[] moves = game.getBoard().getMoves();
        List<String> marks = new ArrayList<>();
        for (int i = 0; i < moves.length; i++) {
            marks.add(i, convertMark(moves[i]));
        }
        return marks;
    }

    private String convertMark(Mark mark) {
        return mark == Mark.NONE ? "" : mark.toString();
    }

    public String formatGameStatus() {
        if (game.isOver()) {
            return game.isWon() ? "Player " + game.winnerMark() + " won!" : "It's a draw!";
        } else {
            return "Active";
        }
    }

    public boolean isAiTurn() {
        return !game.isOver() && game.getCurrentPlayer() instanceof AiPlayer;
    }
}
