package kg.jarkyn.tictactoe_web;

import kg.jarkyn.Board;
import kg.jarkyn.Game;
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
        controller = new TictactoeController();
    }

    @Test
    public void setsWebUI() {
        assertNotNull(getWebUI());
    }

    @Test
    public void setsGame() {
        controller.newGame();

        assertNotNull(getGame());
    }

    @Test
    public void setsUpViewForNewGame() {
        modelAndView = controller.newGame();

        assertEquals("game", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("marks"));
    }

    @Test
    public void playsReceivedMove() {
        controller.newGame();

        controller.game("1");

        assertFalse(getBoard().isValidMove(1));
    }

    @Test
    public void setsUpViewForGamePlay() {
        controller.newGame();

        ModelAndView modelAndView = controller.game("0");

        assertEquals("game", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("marks"));
        assertArrayEquals(new String[]{"X", "", "", "", "", "", "", "", ""}, getWebUI().getMarks());
    }

    @Test
    public void getsGameOver() {
        controller.newGame();

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
