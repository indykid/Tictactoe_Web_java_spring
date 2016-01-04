package kg.jarkyn.tictactoe_web.controllers;

import kg.jarkyn.GameOption;
import kg.jarkyn.tictactoe_web.ParamParser;
import kg.jarkyn.tictactoe_web.Repository;
import kg.jarkyn.tictactoe_web.WebUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/game")
public class TictactoeController {

    private final Repository repo;

    @Autowired
    public TictactoeController(@Qualifier("repo") Repository repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ModelAndView selectGame() {
        ModelAndView modelAndView = new ModelAndView("select_game");
        modelAndView.addObject("gameOptions", GameOption.values());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"gameOption"})
    public ModelAndView newGame(String gameOption) {
        WebUI webUI = setupWebGame(gameOption);
        return new ModelAndView("redirect:/game/" + webUI.hashCode());
    }

    @RequestMapping(value="/{webUIId}", method = RequestMethod.GET)
    public ModelAndView game(@PathVariable("webUIId") int webUIId) {
        WebUI webUI = repo.find(webUIId);

        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("marks", webUI.getMarks());
        modelAndView.addObject("webUIId", webUIId);
        modelAndView.addObject("gameStatus", webUI.formatGameStatus());
        return modelAndView;
    }

    @RequestMapping(value="/{webUIId}", method = RequestMethod.GET, params = {"position"})
    public ModelAndView game(@PathVariable("webUIId") int webUIId, String position) {
        WebUI webUI = repo.find(webUIId);
        playGame(webUI, position);
        return new ModelAndView("redirect:/game/" + webUI.hashCode());
    }

    private WebUI setupWebGame(String numericGameOption) {
        WebUI webUI = new WebUI();
        webUI.setupGame(numericGameOption);
        int id = repo.save(webUI);
        return repo.find(id);
    }

    private void playGame(WebUI webUI, String position) {
        webUI.setHumanMove(ParamParser.parseNumeric(position));
        webUI.playGame();
    }
}
