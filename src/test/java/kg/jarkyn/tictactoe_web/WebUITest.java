package kg.jarkyn.tictactoe_web;

import kg.jarkyn.GameOption;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
    public void setsupGame() {
        ui.setupGame(GameOption.HUMAN_ONLY);

        assertNotNull(ui.getGame());
    }
}
