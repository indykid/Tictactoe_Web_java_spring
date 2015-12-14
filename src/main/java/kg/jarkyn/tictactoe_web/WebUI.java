package kg.jarkyn.tictactoe_web;

import kg.jarkyn.*;

import java.util.List;

public class WebUI implements HumanInput {
    private static final int NULL_MOVE = -1;
    private int humanMove = NULL_MOVE;
    private Game game;

    @Override
    public int getMove(List<Integer> list) {
        return humanMove;
    }

    @Override
    public boolean hasHumanMove() {
        return humanMove != NULL_MOVE;
    }

    public void setHumanMove(int humanMove) {
        this.humanMove = humanMove;
    }

    public void setupGame(GameOption gameOption) {
        this.game = GameFactory.makeGame(new Board(), gameOption, this);
    }

    public Game getGame() {
        return game;
    }
}
