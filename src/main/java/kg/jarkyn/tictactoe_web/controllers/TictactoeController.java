package kg.jarkyn.tictactoe_web.controllers;

import kg.jarkyn.GameOption;
import kg.jarkyn.tictactoe_web.ParamParser;
import kg.jarkyn.tictactoe_web.WebUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class TictactoeController {

    private final WebUI webUI;

    @Autowired
    public TictactoeController(@Qualifier("webUI") WebUI webUI) {
        this.webUI = webUI;
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ModelAndView selectGame() {
        ModelAndView modelAndView = new ModelAndView("select_game");
        modelAndView.addObject("gameOptions", GameOption.values());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"gameOption"})
    public ModelAndView newGame(String gameOption) {
        setupGame(gameOption);
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("marks", webUI.getMarks());
        modelAndView.addObject("status", webUI.formatStatus());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"position"})
    public ModelAndView game(String position) {
        playGame(position);

        ModelAndView modelAndView = new ModelAndView("game");
        if (isGameOver()) {
            modelAndView.addObject("winner", webUI.getWinner());
        }
        modelAndView.addObject("marks", webUI.getMarks());
        modelAndView.addObject("status", webUI.formatStatus());
        return modelAndView;
    }

    private boolean isGameOver() {
        return webUI.isGameOver();
    }

    private void setupGame(String numericGameOption) {
        webUI.setupGame(numericGameOption);
    }

    private void playGame(String position) {
        webUI.setHumanMove(ParamParser.parseNumeric(position));
        webUI.playGame();
    }

    public WebUI getWebUI() {
        return webUI;
    }
}
