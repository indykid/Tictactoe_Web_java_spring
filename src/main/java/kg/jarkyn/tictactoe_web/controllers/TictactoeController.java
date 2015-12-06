package kg.jarkyn.tictactoe_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TictactoeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String selectGame() {

        return "select_game";
    }

}
