package soccer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;


public class Statistics implements Serializable {
    @Column(name="numberOfGoals")
    private Integer numberOfGoals; //Numeric value and should be equal or greater than zero
    @Column(name="numberOfBookings")
    private Integer numberOfBookings; //Numeric value and should be equal or greater than zero
    //Above attributes are mandatory for a statistic

    public Statistics(Integer numberOfGoals, Integer numberOfBookings) {
        this.numberOfGoals = numberOfGoals; //Numeric value and should be equal or greater than zero. Not null
        this.numberOfBookings = numberOfBookings; //Numeric value and should be equal or greater than zero. Not null
    }

    public Integer getNumberOfGoals() {
        return numberOfGoals;
    }

    public void setNumberOfGoals(Integer numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    public Integer getNumberOfBookings() {
        return numberOfBookings;
    }

    public void setNumberOfBookings(Integer numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
    }
}
