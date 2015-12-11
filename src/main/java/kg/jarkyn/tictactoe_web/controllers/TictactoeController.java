package kg.jarkyn.tictactoe_web.controllers;

import kg.jarkyn.*;
import kg.jarkyn.tictactoe_web.ParamParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/game")
public class TictactoeController {
    private static final GameOption DEFAULT_GAME_OPTION = GameOption.HUMAN_ONLY;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView newGame() {
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("moves", "NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE-NONE");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"position", "moves", "mark"})
    public ModelAndView game(String position, String moves, String mark) {
        ModelAndView modelAndView = new ModelAndView("game");

        String updatedMoves = updateMoves(moves, position, mark);
        Game game = GameFactory.makeGame(new Board(ParamParser.parseMoves(updatedMoves)), DEFAULT_GAME_OPTION, new WebUI
                ());

        if (!game.isOver()) {
            modelAndView.addObject("moves", updatedMoves);
            modelAndView.addObject("mark", game.getCurrentPlayer().getMark().toString());
        } else {
            modelAndView = new ModelAndView("game_over");
        }

        return modelAndView;
    }

    private String updateMoves(String moves, String position, String mark) {
        String updatedMoves;
        String[] splitMoves = moves.split("-");
        splitMoves[ParamParser.parseNumeric(position)] = mark;
        return String.join("-", splitMoves);
    }

    private class WebUI implements HumanInput {
        @Override
        public int getMove(List<Integer> list) {
            return 0;
        }

        @Override
        public boolean hasHumanMove() {
            return false;
        }
    }
}
