package kg.jarkyn.tictactoe_web;

import kg.jarkyn.tictactoe_web.controllers.TictactoeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TictactoeControllerWithMinimalSpringSetupTest {
    private MockMvc mockMvc;
    private WebUI webUI;

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
        mockMvc.perform(get("/game/select"))
                .andExpect(status().isOk())
                .andExpect(view().name("select_game"));
    }

    @Test
    public void getNewGame() throws Exception {
        mockMvc.perform(get("/game?gameOption=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"));
    }

    @Test
    public void getsGame() throws Exception {
        String hvhNumericOption = "3";
        webUI.setupGame(hvhNumericOption);
        MvcResult result = playMoves(new String[]{"0"});

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game", modelAndView.getViewName());
    }

    @Test
    public void getsGameOver() throws Exception {
        String hvhNumericOption = "3";
        webUI.setupGame(hvhNumericOption);
        MvcResult result = playMoves(new String[]{"0", "4", "3", "6", "2", "1", "7", "5", "8"});

        ModelAndView modelAndView = result.getModelAndView();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game_over", modelAndView.getViewName());
        assertEquals("", modelAndView.getModel().get("winner"));
    }

    private MvcResult playMoves(String[] positions) throws Exception {
        MvcResult result = null;
        String urlPartial = "/game?position=";
        for (String position : positions) {
            String url = urlPartial + position;
            result = mockMvc.perform(get(url)).andReturn();
        }
        return result;
    }
}
