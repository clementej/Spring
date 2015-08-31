package soccer.web;


public class PlayerNotFoundException extends RuntimeException{
    private String playerId;
    public PlayerNotFoundException(String playerId) {
        this.playerId = playerId;
    }
    public String getPlayerId() {
        return playerId;
    }
}
