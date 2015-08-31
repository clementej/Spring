package soccer.entities;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Trainer implements Serializable {
    @NotNull
    @Id
    @Column(name="firstName")
    private String firstName; //Can not be empty
    @NotNull
    @Id
    @Column(name="lastName")
    private String lastName; //Can not be empty
    @NotNull
    @Column(name="age")
    private Integer age; //Should be equal or greater than 40

    @Transient
    private List<Team> previousTeamList=new LinkedList<Team>();



    @Transient
    private Money annualSalary; //Can not be empty

    public Trainer() {
    }

    public Trainer(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Trainer(String firstName, String lastName, Integer age, List<Team> previousTeamList, Money annualSalary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.previousTeamList = previousTeamList;
        this.annualSalary = annualSalary;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public Integer getAge() {return age;}

    public void setAge(Integer age) {this.age = age;}

    public List<Team> getPreviousTeamList() {return previousTeamList;}

    public void setPreviousTeamList(List<Team> previousTeamList) {this.previousTeamList = previousTeamList;}

    public Money getAnnualSalary() {return annualSalary;}

    public void setAnnualSalary(Money annualSalary) {this.annualSalary = annualSalary;}

    public String getId(){
        return this.firstName+" "+this.lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (!(obj instanceof Trainer)) return false;
        return this.getId().equals(((Trainer)obj).getId());
    }
}
