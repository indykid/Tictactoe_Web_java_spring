package kg.jarkyn.tictactoe_web;

import kg.jarkyn.GameOption;
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
    private Repository repo;
    private MvcResult result;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".html");
        repo = new Repository();
        mockMvc = standaloneSetup(new TictactoeController(repo)).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getsGameSelection() throws Exception {
        result = sendGet("/game/select");

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("select_game", modelAndView.getViewName());
        assertArrayEquals(GameOption.values(), (Object[]) modelAndView.getModel().get("gameOptions"));
    }

    @Test
    public void storesNewUI() throws Exception {
        sendGet("/game?gameOption=" + cvhOption);

        assertFalse(repo.isEmpty());
    }

    @Test
    public void setsGame() throws Exception {
        sendGet("/game?gameOption=" + cvhOption);

        webUI = repo.getLast();
        assertNotNull(webUI.getGame());
    }

    @Test
    public void getNewGame() throws Exception {
        result = sendGet("/game?gameOption=" + cvhOption);

        webUI = repo.getLast();
        assertEquals(302, result.getResponse().getStatus());
        assertEquals("/game/" + repo.getLastId(), result.getResponse().getRedirectedUrl());
    }

    @Test
    public void getsSpecificGame() throws Exception {
        setupWebUI(hvhOption);
        int id = repo.getLastId();

        result = sendGet("/game/" + id);

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game", modelAndView.getViewName());
        assertEquals("Active", modelAndView.getModel().get("gameStatus"));
        assertEquals(id, modelAndView.getModel().get("webUIId"));
        assertArrayEquals((Object[]) modelAndView.getModel().get("marks"), repo.find(id).getMarks());
    }

    @Test
    public void playsSpecificGame() throws Exception {
        setupWebUI(hvhOption);
        int id = repo.getLastId();

        result = sendGet("/game/" + id + "?position=0");

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(302, result.getResponse().getStatus());
        assertEquals("redirect:/game/" + id, modelAndView.getViewName());
    }

    @Test
    public void getsGameOver() throws Exception {
        setupWebUI(hvhOption);
        int id = repo.getLastId();
        playMoves(id, new String[]{"0", "4", "3", "6", "2", "1", "7", "5", "8"});

        result = sendGet("/game/" + id);

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game", modelAndView.getViewName());
        assertEquals("It's a draw!", modelAndView.getModel().get("gameStatus"));
        assertEquals(id, modelAndView.getModel().get("webUIId"));
        assertArrayEquals((Object[]) modelAndView.getModel().get("marks"), repo.find(id).getMarks());
    }

    @Test
    public void handlesMultipleGames() throws Exception {
        setupWebUI(hvhOption);
        int firstId = repo.getLastId();
        setupWebUI(hvhOption);
        int lastId = repo.getLastId();

        MvcResult firstResult = sendGet("/game/" + firstId + "?position=0");
        MvcResult lastResult = sendGet("/game/" + lastId + "?position=1");

        assertEquals(302, firstResult.getResponse().getStatus());
        assertEquals("redirect:/game/" + firstId, firstResult.getModelAndView().getViewName());

        assertEquals(302, lastResult.getResponse().getStatus());
        assertEquals("redirect:/game/" + lastId, lastResult.getModelAndView().getViewName());
    }

    private void setupWebUI(String gameOption) {
        webUI = new WebUI();
        repo.save(webUI);
        webUI.setupGame(gameOption);
    }

    private void playMoves(int webUIId, String[] positions) throws Exception {
        String urlPartial = "/game/" + webUIId + "?position=";
        for (String position : positions) {
            result = sendGet(urlPartial + position);
        }
    }

    private MvcResult sendGet(String url) throws Exception {
        return mockMvc.perform(get(url)).andReturn();
    }
}
