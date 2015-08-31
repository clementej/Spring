package soccer.entities;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.io.Serializable;

public class Team implements Serializable {
    private String name; //Can not be empty
    private Trainer trainer; //Must have one
    private Player[] players=new Player[22]; //Must have each of them
    private Integer yearOfFoundation; //Should be equal or greater than 1950

    public Team() {
    }

    public Team(String name, Trainer trainer, Player[] players, Integer yearOfFoundation) {
        this.name = name;
        this.trainer = trainer;
        this.players = players;
        this.yearOfFoundation = yearOfFoundation;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Trainer getTrainer() {return trainer;}

    public void setTrainer(Trainer trainer) {this.trainer = trainer;}

    public Player[] getPlayers() {return players;}

    public void setPlayers(Player[] players) {this.players = players;}

    public Integer getYearOfFoundation() {return yearOfFoundation;}

    public void setYearOfFoundation(Integer yearOfFoundation) {this.yearOfFoundation = yearOfFoundation;}

    @Override
    public boolean equals(Object obj) {
        boolean returnValue=false;
        try {
            returnValue = this.name.equals(((Team) obj).name);
        } catch (RuntimeException e){
            return false;
        }
        return returnValue;
    }
}
