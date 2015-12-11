package kg.jarkyn.tictactoe_web;

import kg.jarkyn.tictactoe_web.controllers.TictactoeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TictactoeControllerWithoutSpringSetupTest {

    private TictactoeController controller;
    private ModelAndView modelAndView;

    @Before
    public void setUp() throws Exception {
        controller = new TictactoeController();
    }

    @Test
    public void noMovesForNewGame() {
        modelAndView = controller.newGame();

        Map model = modelAndView.getModel();
        assertEquals("game", modelAndView.getViewName());
        assertEquals("NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE",
                model.get("moves"));
    }

    @Test
    public void playsReceivedPosition() {
        modelAndView = controller.game("0", "NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE", "X");

        Map model = modelAndView.getModel();
        assertEquals("game", modelAndView.getViewName());
        assertEquals("X-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE", model.get("moves"));
        assertEquals("O", model.get("mark"));
    }

    @Test
    public void getsGameOver() {
        modelAndView = controller.game("8", "X-O-X-X-O-O-O-X-NONE", "X");

        assertEquals("game_over", modelAndView.getViewName());
    }
}
