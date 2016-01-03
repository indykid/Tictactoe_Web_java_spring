package kg.jarkyn.tictactoe_web;

import kg.jarkyn.Mark;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class WebUITest {

    private WebUI ui;
    private final String hvhOption = "3";
    private final String cvhOption = "1";
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
    public void playsGame() {
        ui.setupGame(hvhOption);
        ui.setHumanMove(0);

        ui.playGame();

        assertFalse(ui.getGame().getBoard().isValidMove(0));
    }

    @Test
    public void gameIsOver() {
        ui.setupGame(cvcOption);
        ui.getGame().play();

        assertTrue(ui.isGameOver());
    }

    @Test
    public void gameIsNotOver() {
        ui.setupGame(hvhOption);

        assertFalse(ui.isGameOver());
    }

    @Test
    public void convertsX() {
        assertEquals("X", ui.convertMark(Mark.X));
    }

    @Test
    public void convertsNONE() {
        assertEquals("", ui.convertMark(Mark.NONE));
    }

    @Test
    public void getsMarks() {
        ui.setupGame(hvhOption);
        ui.setHumanMove(0);

        ui.playGame();

        assertArrayEquals(new String[]{"X", "", "", "", "", "", "", "", ""}, ui.getMarks());
    }

    @Test
    public void getsNoWinner() {
        ui.setupGame(cvhOption);
        ui.getGame().play();

        assertEquals("", ui.getWinner());
    }

    @Test
    public void playsAiMove() {
        ui.setupGame(cvhOption);

        assertTrue(contains("X", ui.getMarks()));
    }

    @Test
    public void formatsGameActiveStatus(){
        ui.setupGame(cvhOption);
        ui.playGame();

        assertEquals("Active", ui.formatStatus());
    }

    @Test
    public void formatsDrawStatus() {
        ui.setupGame(cvcOption);
        ui.getGame().play();

        assertEquals("It's a draw!", ui.formatStatus());
    }

    @Test
    public void formatsGameWonStatus() {
        ui.setupGame(hvhOption);
        playMoves(Arrays.asList(0, 1, 3, 4, 6));

        assertEquals("Player X won!", ui.formatStatus());
    }

    private void playMoves(List<Integer> moves) {
        for (int move : moves) {
            ui.setHumanMove(move);
            ui.playGame();
        }
    }

    private boolean contains(String mark, String[] marks) {
        return Arrays.asList(marks).indexOf(mark) != -1;
    }
}
