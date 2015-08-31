package soccer.data;

import soccer.entities.Player;

import java.io.IOException;
import java.util.List;

public interface PlayerRepository {
    public List<Player> findPlayers(int max, int count) throws IOException;
    List<Player> findPlayers(int max, int count,int startingAt) throws IOException;
    Player  findOne(String playerId);
    public Player save(Player player);
}
