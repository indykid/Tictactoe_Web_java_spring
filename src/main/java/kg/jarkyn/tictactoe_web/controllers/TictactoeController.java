package kg.jarkyn.tictactoe_web.controllers;

import kg.jarkyn.GameOption;
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

        return modelAndView;
    }
}
