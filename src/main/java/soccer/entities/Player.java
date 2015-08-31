package soccer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
public class Player implements Serializable{
    @NotNull
    @Column(name="firstName")
    @Id
    private String firstName; //Can not be empty and can not contain numbers
    @NotNull
    @Column(name="lastName")
    @Id
    private String lastName; //Can not be empty and can not contain numbers
    @NotNull
    @Column(name="age")
    private Integer age; //Should be between 20 and 23 years old
    @NotNull
    @Column(name="countryOfBirth")
    private String countryOfBirth; //Can not be empty and can not contain numbers

    @Transient
    private Money annualSalary; //Can not be empty

    @Transient
    private Position position;


    @Transient
    private Statistics statistics;

    public Player() {
    }

    public Player(String firstName, String lastName, Integer age, String countryOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.countryOfBirth = countryOfBirth;
    }


    public Player(String firstName, String lastName, Integer age, String countryOfBirth, Money annualSalary, Position position, Statistics statistics) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.countryOfBirth = countryOfBirth;
        this.annualSalary = annualSalary;
        this.position = position;
        this.statistics = statistics;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public Money getAnnualSalary() {return annualSalary;}

    public void setAnnualSalary(Money annualSalary) {this.annualSalary = annualSalary;}

    public Position getPosition() {return position;}

    public void setPosition(Position position) {this.position = position;}

    public Statistics getStatistics() {return statistics;}

    public void setStatistics(Statistics statistics) {this.statistics = statistics;}

    public String getId(){
        return this.firstName+" "+this.lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof Player)) return false;
        return this.getId().equals(((Player)obj).getId());
    }
}
