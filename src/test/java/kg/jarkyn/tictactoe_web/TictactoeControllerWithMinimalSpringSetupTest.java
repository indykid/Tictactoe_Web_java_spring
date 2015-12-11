package kg.jarkyn.tictactoe_web;

import kg.jarkyn.tictactoe_web.controllers.TictactoeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TictactoeControllerWithMinimalSpringSetupTest {
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".html");
        mockMvc = standaloneSetup(new TictactoeController()).setViewResolvers(viewResolver).build();
    }

    @Test
    public void getNewGame() throws Exception {
        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"));
    }

    @Test
    public void getsGame() throws Exception {
        MvcResult result = mockMvc.perform(get("/game?moves=NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE&position=0" +
                "&mark=X")).andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        Map model = modelAndView.getModel();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game", modelAndView.getViewName());
        assertEquals("X-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE", model.get("moves"));
        assertEquals("O", model.get("mark"));
    }

    @Test
    public void getsGameOver() throws Exception {
        MvcResult result = mockMvc.perform(get("/game?position=8&moves=X-O-X-X-O-O-O-X-NONE&mark=X")).andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        Map model = modelAndView.getModel();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("game_over", modelAndView.getViewName());
        assertNull(model.get("moves"));
        assertNull(model.get("mark"));
    }
}
