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

}
