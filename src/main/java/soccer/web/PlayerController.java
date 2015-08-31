package soccer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import soccer.config.Helper;
import soccer.entities.Money;
import soccer.entities.Player;
import soccer.data.PlayerRepository;
import soccer.entities.Position;
import soccer.entities.Statistics;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final String MAX_INTEGER_AS_STRING = "2147483647";//Integer.toString(Integer.MAX_VALUE);
    private PlayerRepository playerRepository;


    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository=playerRepository;
    }

    @Autowired
    Helper helper;

    @RequestMapping(method= RequestMethod.GET)
    List<Player> players(@RequestParam(value="max",
            defaultValue= MAX_INTEGER_AS_STRING) int max,
                         @RequestParam(value="count", defaultValue="20") int count) throws IOException {
        return playerRepository.findPlayers(max,count);
    }

    @RequestMapping(value="/show", method=RequestMethod.GET)
    public String showPlayer(
            @RequestParam("player_id") String playerId,
            Model model) {
        model.addAttribute(playerRepository.findOne(playerId));
        return "player";
    }

    @RequestMapping(value="/{playerId}", method=RequestMethod.GET)
    public String player(
            @PathVariable("playerId") String playerId,
            Model model) {
        model.addAttribute(playerRepository.findOne(playerId));
        return "player";
    }


    @RequestMapping(value="/registerplayer", method=POST)
    public String processRegistration(
            @RequestParam(value="position", defaultValue="Goalkeeper") Position position,
            @RequestParam(value="salary", defaultValue="0") BigDecimal salary,
            @RequestParam(value="goals", defaultValue="0") int goals,
            @RequestParam(value="bookings", defaultValue="0") int bookings,
            @Valid Player player, Errors errors) {
        Statistics statistics=new Statistics(goals,bookings);
        player.setStatistics(statistics);
        player.setPosition(position);
        Money money=new Money(salary);
        player.setAnnualSalary(money);
        if (errors.hasErrors()) {
            return "/registerplayer";
        }

        if (!helper.validatePlayer(player))
            return "/registerplayer";

        playerRepository.save(player);
        return "redirect:/players/" + player.getId();
    }


    @RequestMapping(value="/registerplayer", method=GET)
    public String showRegistrationForm() {
        return "registerplayer";
    }

}
