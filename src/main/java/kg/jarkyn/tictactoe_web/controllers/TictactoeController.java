package kg.jarkyn.tictactoe_web.controllers;

import kg.jarkyn.Game;
import kg.jarkyn.GameOption;
import kg.jarkyn.tictactoe_web.ParamParser;
import kg.jarkyn.tictactoe_web.WebUI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class TictactoeController {
    private static final GameOption DEFAULT_GAME_OPTION = GameOption.HUMAN_ONLY;
    private WebUI webUI = new WebUI();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView newGame() {
        setupGame();
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("marks", webUI.getMarks());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"position"})
    public ModelAndView game(String position) {
        playGame(position);

        if (isGameOver()) {
            ModelAndView modelAndView = new ModelAndView("game_over");
            modelAndView.addObject("winner", webUI.getWinner());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("game");
            modelAndView.addObject("marks", webUI.getMarks());
            return modelAndView;
        }
    }

    private boolean isGameOver() {
        return webUI.isGameOver();
    }

    private void setupGame() {
        Game game = webUI.getGame();
        if (game == null || game.isOver()) {
            webUI.setupGame(DEFAULT_GAME_OPTION);
        }
    }

    private void playGame(String position) {
        setupGame();
        webUI.setHumanMove(ParamParser.parseNumeric(position));
        webUI.playGame();
    }

    public WebUI getWebUI() {
        return webUI;
    }
}
