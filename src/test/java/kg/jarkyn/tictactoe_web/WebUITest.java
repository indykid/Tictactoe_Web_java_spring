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
        String numericGameOption = "1";
        ui.setupGame(numericGameOption);

        assertNotNull(ui.getGame());
    }

    @Test
    public void playsGame() {
        String numericGameOption = "3";
        ui.setupGame(numericGameOption);
        ui.setHumanMove(0);

        ui.playGame();

        assertFalse(ui.getGame().getBoard().isValidMove(0));
    }

    @Test
    public void gameIsOver() {
        String aiVsAiNumericOption = "4";
        ui.setupGame(aiVsAiNumericOption);
        ui.getGame().play();

        assertTrue(ui.isGameOver());
    }

    @Test
    public void gameIsNotOver() {
        String hvhNumericOption = "3";
        ui.setupGame(hvhNumericOption);

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
        String humanVsHumannNumericOption = "3";
        ui.setupGame(humanVsHumannNumericOption);
        ui.setHumanMove(0);

        ui.playGame();

        assertArrayEquals(new String[]{"X", "", "", "", "", "", "", "", ""}, ui.getMarks());
    }

    @Test
    public void getsNoWinner() {
        String aiVsHumanNumericOption = "1";
        ui.setupGame(aiVsHumanNumericOption);
        ui.getGame().play();

        assertEquals("", ui.getWinner());
    }

    @Test
    public void playsAiMove() {
        String aiVsHumanNumericOption = "1";
        ui.setupGame(aiVsHumanNumericOption);

        assertTrue(contains("X", ui.getMarks()));
    }

    @Test
    public void formatsGameActiveStatus(){
        String aiVsHumanNumericOption = "1";
        ui.setupGame(aiVsHumanNumericOption);
        ui.playGame();

        assertEquals("Active", ui.formatStatus());
    }

    @Test
    public void formatsDrawStatus() {
        String aiVsAiNumericOption = "4";
        ui.setupGame(aiVsAiNumericOption);
        ui.getGame().play();

        assertEquals("It's a draw!", ui.formatStatus());
    }

    @Test
    public void formatsGameWonStatus() {
        String humanVsHumanNumericOption = "3";
        ui.setupGame(humanVsHumanNumericOption);
        playMoves(Arrays.asList(0, 1, 3, 4, 6));

        assertEquals("Player X has won this game!", ui.formatStatus());
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
