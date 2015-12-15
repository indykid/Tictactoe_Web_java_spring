package kg.jarkyn.tictactoe_web;

import kg.jarkyn.*;
import kg.jarkyn.tictactoe_web.controllers.TictactoeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;

public class TictactoeControllerWithoutSpringSetupTest {

    private TictactoeController controller;
    private ModelAndView modelAndView;

    @Before
    public void setUp() throws Exception {
        controller = new TictactoeController(new WebUI());
    }

    @Test
    public void selectsGame() {
        modelAndView = controller.selectGame();

        assertEquals("select_game", modelAndView.getViewName());
        assertArrayEquals(GameOption.values(), (Object[]) modelAndView.getModel().get("gameOptions"));
    }

    @Test
    public void setsGame() {
        controller.newGame("1");

        assertNotNull(getGame());
    }

    @Test
    public void setsCorrectGame() {
        String aiFirstGameOption = "1";
        controller.newGame(aiFirstGameOption);

        assertTrue(getGame().getPlayerX() instanceof AiPlayer);
        assertTrue(getGame().getPlayerO() instanceof HumanPlayer);
    }

    @Test
    public void setsUpViewForNewGame() {
        String gameOption = "1";
        modelAndView = controller.newGame(gameOption);

        assertEquals("game", modelAndView.getViewName());
        assertEquals(modelAndView.getModel().get("marks"), getWebUI().getMarks());
    }

    @Test
    public void playsReceivedMove() {
        String gameOption = "3";
        controller.newGame(gameOption);

        controller.game("0");

        assertFalse(getBoard().isValidMove(0));
    }

    @Test
    public void setsUpViewForGamePlay() {
        String gameOption = "3";
        controller.newGame(gameOption);

        ModelAndView modelAndView = controller.game("0");

        assertEquals("game", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("marks"));
        assertArrayEquals(new String[]{"X", "", "", "", "", "", "", "", ""}, getWebUI().getMarks());
    }

    @Test
    public void getsGameOver() {
        String gameOption = "3";
        controller.newGame(gameOption);

        playMoves(new String[]{"0", "4", "3", "6", "2", "1", "7", "5", "8"});

        assertEquals("game_over", modelAndView.getViewName());
        assertEquals("", modelAndView.getModel().get("winner"));
    }

    private void playMoves(String[] moves) {
        for (String move : moves) {
            modelAndView = controller.game(move);
        }
    }

    private Game getGame() {
        return getWebUI().getGame();
    }

    private WebUI getWebUI() {
        return controller.getWebUI();
    }

    private Board getBoard() {
        return getGame().getBoard();
    }
}
