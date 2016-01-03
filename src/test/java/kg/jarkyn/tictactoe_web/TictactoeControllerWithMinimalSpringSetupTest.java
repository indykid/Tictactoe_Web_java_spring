package kg.jarkyn.tictactoe_web;

import kg.jarkyn.AiPlayer;
import kg.jarkyn.GameOption;
import kg.jarkyn.HumanPlayer;
import kg.jarkyn.tictactoe_web.controllers.TictactoeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TictactoeControllerWithMinimalSpringSetupTest {
    private final String cvhOption = "1";
    private final String hvhOption = "3";
    private MockMvc mockMvc;
    private WebUI webUI;
    private MvcResult result;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".html");
        webUI = new WebUI();
        mockMvc = standaloneSetup(new TictactoeController(webUI)).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getsGameSelection() throws Exception {
        sendGet("/game/select");

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("select_game", modelAndView.getViewName());
        assertArrayEquals(GameOption.values(), (Object[]) modelAndView.getModel().get("gameOptions"));
    }

    @Test
    public void setsGame() throws Exception {
        sendGet("/game?gameOption=" + cvhOption);

        assertNotNull(webUI.getGame());
    }

    @Test
    public void setsCorrectGame() throws Exception {
        sendGet("/game?gameOption=" + cvhOption);

        assertTrue(webUI.getGame().getPlayerX() instanceof AiPlayer);
        assertTrue(webUI.getGame().getPlayerO() instanceof HumanPlayer);
    }

    @Test
    public void getNewGame() throws Exception {
        sendGet("/game?gameOption=" + cvhOption);

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game", modelAndView.getViewName());
        assertEquals("Active", modelAndView.getModel().get("status"));
        assertArrayEquals((Object[]) modelAndView.getModel().get("marks"), webUI.getMarks());
    }

    @Test
    public void getsGame() throws Exception {
        webUI.setupGame(hvhOption);

        playMoves(new String[]{"0"});

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game", modelAndView.getViewName());
        assertEquals("Active", modelAndView.getModel().get("status"));
        assertArrayEquals((Object[]) modelAndView.getModel().get("marks"), webUI.getMarks());
    }

    @Test
    public void getsGameOver() throws Exception {
        webUI.setupGame(hvhOption);

        playMoves(new String[]{"0", "4", "3", "6", "2", "1", "7", "5", "8"});

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals("game", modelAndView.getViewName());
        assertEquals("It's a draw!", modelAndView.getModel().get("status"));
        assertArrayEquals((Object[]) modelAndView.getModel().get("marks"), webUI.getMarks());
    }

    private void playMoves(String[] positions) throws Exception {
        String urlPartial = "/game?position=";
        for (String position : positions) {
            sendGet(urlPartial + position);
        }
    }

    private void sendGet(String url) throws Exception {
        result = mockMvc.perform(get(url)).andReturn();
    }
}
