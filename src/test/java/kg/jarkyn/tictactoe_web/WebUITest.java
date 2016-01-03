package kg.jarkyn.tictactoe_web;

import kg.jarkyn.AiPlayer;
import kg.jarkyn.HumanPlayer;
import kg.jarkyn.Mark;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class WebUITest {

    private WebUI ui;
    private final String cvhOption = "1";
    private final String hvhOption = "3";
    private final String cvcOption = "4";

    @Before
    public void setUp() {
        ui = new WebUI();
    }

    @Test
    public void hasNoHumanMoveAtTheStart() {
        WebUI ui = new WebUI();

        assertFalse(ui.hasHumanMove());
    }

    @Test
    public void hasHumanMoveWhenReceivedOne() {
        setUp();
        ui.setHumanMove(0);

        assertTrue(ui.hasHumanMove());
    }

    @Test
    public void getsMove() {
        ui.setHumanMove(1);
        List<Integer> irrelevant = new ArrayList<>();

        assertEquals(1, ui.getMove(irrelevant));
    }

    @Test
    public void resetsHumanMove() {
        ui.setHumanMove(1);
        List<Integer> irrelevant = new ArrayList<>();
        ui.getMove(irrelevant);

        assertFalse(ui.hasHumanMove());
    }

    @Test
    public void setsGame() {
        ui.setupGame(cvhOption);

        assertNotNull(ui.getGame());
    }

    @Test
    public void setsCorrectGame() throws Exception {
        ui.setupGame(cvhOption);

        assertTrue(ui.getGame().getPlayerX() instanceof AiPlayer);
        assertTrue(ui.getGame().getPlayerO() instanceof HumanPlayer);
    }

    @Test
    public void placesPlayedMovesOnTheBoard() {
        ui.setupGame(hvhOption);

        playMoves(Arrays.asList(0));

        assertEquals(Mark.X, ui.getGame().getBoard().markAt(0));
    }

    @Test
    public void gameIsNotOver() {
        ui.setupGame(hvhOption);

        assertFalse(ui.isGameOver());
    }

    @Test
    public void gameIsOver() {
        ui.setupGame(hvhOption);

        playMoves(Arrays.asList(0, 4, 3, 6, 2, 1, 7, 5, 8));

        assertTrue(ui.isGameOver());
    }

    @Test
    public void formatsMarks() {
        ui.setupGame(hvhOption);

        playMoves(Arrays.asList(0));

        assertEquals(Arrays.asList("X", "", "", "", "", "", "", "", ""), ui.formatMarks());
    }

    @Test
    public void formatsGameActiveStatus(){
        ui.setupGame(cvhOption);

        assertEquals("Active", ui.formatGameStatus());
    }

    @Test
    public void formatsDrawStatus() {
        ui.setupGame(hvhOption);

        playMoves(Arrays.asList(0, 4, 3, 6, 2, 1, 7, 5, 8));

        assertEquals("It's a draw!", ui.formatGameStatus());
    }

    @Test
    public void formatsGameWonStatus() {
        ui.setupGame(hvhOption);

        playMoves(Arrays.asList(0, 1, 3, 4, 6));

        assertEquals("Player X won!", ui.formatGameStatus());
    }

    @Test
    public void notAiTurn() {
        ui.setupGame(hvhOption);

        assertFalse(ui.isAiTurn());
    }

    @Test
    public void isAiTurn() {
        ui.setupGame(cvcOption);



        assertTrue(ui.isAiTurn());
    }

    @Test
    public void notAiTurnWhenOver() {
        ui.setupGame(cvcOption);

        playAllAiMoves();

        assertFalse(ui.isAiTurn());
    }

    private void playAllAiMoves() {
        for (int i = 0; i < ui.getGame().getBoard().getSize(); i++) {
            ui.playGame();
        }
    }

    private void playMoves(List<Integer> moves) {
        for (int move : moves) {
            ui.setHumanMove(move);
            ui.playGame();
        }
    }
}
