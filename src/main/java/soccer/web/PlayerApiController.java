package soccer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccer.config.Helper;
import soccer.data.PlayerRepository;
import soccer.entities.Player;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/playersapi")
public class PlayerApiController {
    private final String MAX_INTEGER_AS_STRING = "2147483647";//Integer.toString(Integer.MAX_VALUE);
    private PlayerRepository playerRepository;


    @Autowired
    public PlayerApiController(PlayerRepository playerRepository) {
        this.playerRepository=playerRepository;
    }

    @Autowired
    Helper helper;

    @RequestMapping(method=RequestMethod.GET,
            produces="application/json")
    public @ResponseBody List<Player> players(@RequestParam(value="max",
            defaultValue= MAX_INTEGER_AS_STRING) int max,
                         @RequestParam(value="count", defaultValue="20") int count) throws IOException {
        return playerRepository.findPlayers(max,count);
    }

    @RequestMapping(
            method=RequestMethod.POST,
            consumes="application/json")
    public @ResponseBody
    Player savePlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }

    @RequestMapping(value="/{playerId}", method=RequestMethod.GET)
    public Player player(@PathVariable String playerId) {
        Player player = playerRepository.findOne(playerId);
        if (player==null) throw new PlayerNotFoundException(playerId);
        return player;
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> playerNotFound(
            PlayerNotFoundException e) {
        String playerId = e.getPlayerId();
        Error error = new Error(4, "Player [" + playerId + "] not found");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
}
