package kg.jarkyn.tictactoe_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TictactoeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView selectGame() {

        ModelAndView modelAndView = new ModelAndView("select_game");
        modelAndView.addObject("gameOptions", GameOption.values());

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public ModelAndView newGame() {
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("moves", "NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE");
        return modelAndView;
    }
}
