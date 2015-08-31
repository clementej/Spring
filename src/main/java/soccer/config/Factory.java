package soccer.config;

import org.springframework.stereotype.Component;
import soccer.entities.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.io.*;

@Component
public  class Factory {

    public Factory() {
    }


    public static Factory getInstance(){

        return new Factory();
    }


    String fileName = "/Users/clementearismendi/IdeaProjects/registerPlayer/src/main/java/file/names";
    String fileLastName = "/Users/clementearismendi/IdeaProjects/registerPlayer/src/main/java/file/lastNames";


    //String fileName = "./files/names";
    //String fileLastName = "./files/lastNames";


    ArrayList<String> allNames = new ArrayList<String>();

    ArrayList<String> allLastNames = new ArrayList<String>();

    Random r = new Random();

    private  String firstName;
    private  String lastName;
    private  Integer age;
    private  String countryOfBirth;
    private Money annualSalary;
    private Position position;
    private Statistics statistics;
    private List<Team> previousTeamList;
    int minFoundationYear=1950;
    private int fistTrainerAge=((new GregorianCalendar()).get(Calendar.YEAR))-minFoundationYear;
    private Trainer firstRandomTrainer=null;

    private Trainer getFirstRandomTrainer() throws IOException {
        if (firstRandomTrainer ==null){
            firstRandomTrainer =new Trainer(generateRandomFisrtName(),generateRandomLastName(),fistTrainerAge,null,generateRandomAnnualSalary());
        }
        return firstRandomTrainer;
    }

    Team firstRandomTeam=null;

    private Team getFirstRandomTeam() throws IOException {
        if (firstRandomTeam==null){
            firstRandomTeam=new Team(generateRandomTeamName(),null,generatePlayersArray(22), generateRandomYearOfFoundation());
            firstRandomTeam.setYearOfFoundation(minFoundationYear);
            firstRandomTeam.setTrainer(getFirstRandomTrainer());
            List<Team> firstTrainerPreviousTeamList=new ArrayList<Team>();
            firstTrainerPreviousTeamList.add(firstRandomTeam);
            getFirstRandomTrainer().setPreviousTeamList(firstTrainerPreviousTeamList);
            existingTeams.add(firstRandomTeam);
        }
        return firstRandomTeam;
    }



    List<Team> existingTeams=new ArrayList<Team>();

    public Player[] generatePlayersArray(int length) throws IOException {
        if (length <1) return null;
        Player[] playerArray=new Player[length];
        for (int i=0;i<playerArray.length;i++)
            playerArray[i]=generateRandomPlayer();
        return playerArray;
    }

    public Team generateRandomTeam() throws IOException {
        if (existingTeams.size()>=teamNameList.length) throw new RuntimeException("Max Number of Teams Already Created");
        Team team=null;
        if (existingTeams.size()<1){
            return getFirstRandomTeam();
        }else {
            team=new Team(generateRandomTeamName(),generateRandomTrainer(),generatePlayersArray(22), generateRandomYearOfFoundation());
            existingTeams.add(team);
        }

        return team;
    }

    private Team previousTeamTemp;

    public Trainer generateRandomTrainer() throws IOException {
        Trainer trainer;
        firstName=generateRandomFisrtName();
        lastName=generateRandomLastName();
        age=generateRandomTrainerAge();
        annualSalary=generateRandomAnnualSalary();
        previousTeamList=new ArrayList<Team>();
        if (existingTeams.size()>1){
            int previousTeamNumber=random.nextInt(existingTeams.size()+1)-1;
            for (int i=0;i<=previousTeamNumber+1;i++){

                previousTeamTemp=chooseRandomExistingTeam();
                if (!previousTeamList.contains(previousTeamTemp)){
                    previousTeamList.add(previousTeamTemp);
                }

            }
        } else {
            previousTeamList.add(getFirstRandomTeam());
        }
        trainer=new Trainer(firstName,lastName,age,previousTeamList,annualSalary);
        return trainer;
    }

    private int randomIndexTemp;

    private Team chooseRandomExistingTeam(){
        Team teamTemp=null;
        randomIndexTemp=random.nextInt(existingTeams.size());
        teamTemp=existingTeams.get(randomIndexTemp);
        return teamTemp;
    }

    public  Player generateRandomPlayer() throws IOException {
        Player player;
        firstName=generateRandomFisrtName();
        lastName=generateRandomLastName();
        age=generateRandomPlayerAge();
        countryOfBirth=generateRandomCountryOfBirth();
        annualSalary=generateRandomAnnualSalary();
        Position position=generateRandomPosition();
        Statistics statistics=generateRandomStatistics();
        player=new Player(firstName,lastName,age,countryOfBirth,annualSalary,position,statistics);
        return player;
    }

    private  Random random=new Random();

    private  String generateRandomFisrtName() throws IOException {


        BufferedReader in = new BufferedReader(new FileReader(fileName));
        while (true) {
            if (!(in.ready())) {
                break;
            }
            allNames.add( in.readLine() );
        }
        in.close();

        String randomName = allNames.get(r.nextInt(allNames.size()));


        return randomName;

    }

    private  String generateRandomLastName() throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(fileLastName));
        while (true) {
            if (!(in.ready())) {
                break;
            }
            allLastNames.add( in.readLine() );
        }
        in.close();

        String randomLastName = allLastNames.get(r.nextInt(allLastNames.size()));
        return randomLastName;

    }

    private int generateRandomPlayerAge(){
        return 20 + random.nextInt(3);
    }

    private int generateRandomTrainerAge(){
        return 40 + random.nextInt(40);
    }

    private  String [] countryNamesList=retrieveISOCountryNamesList();

    public String[] retrieveISOCountryNamesList(){

        String[] locales = Locale.getISOCountries();
        String[] countryNamesListTemp=new String[locales.length];

        int i=0;
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryNamesListTemp[i]=obj.getDisplayCountry();
            i++;
        }
        return countryNamesListTemp;
    }

    private  String generateRandomCountryOfBirth(){
        return countryNamesList[random.nextInt(countryNamesList.length)];
    }

    private  Money generateRandomAnnualSalary(){
        return new Money(new BigDecimal(50000+random.nextInt(10)*10),Currency.getInstance(Locale.CANADA), RoundingMode.CEILING);
    }

    private  Position generateRandomPosition(){
        return Position.values()[random.nextInt(Position.values().length)];
    }

    private  final int reasonableMaxNumberOfGoals=1000;
    private  final int reasonableMaxNumberOfBookins=500;

    private  Statistics generateRandomStatistics(){
        return new Statistics(1+random.nextInt(reasonableMaxNumberOfGoals),1+random.nextInt(reasonableMaxNumberOfBookins));
    }

    public int getMaxNumberOfTeams(){
        return teamNameList.length;
    }
    String[] teamNameList={
            "Buffalo Bills", "Cincinnati Bengals", "Cleveland Browns", "Denver Broncos", "Houston Texans", "Indianapolis Colts" , "Jacksonville Jaguars" , "Kansas City Chiefs", "Miami Dolphins", "New England Patriots" , "New York Jets", "Oakland Raiders" , "Pittsburgh Steelers" , "San Diego Chargers" , "Tennessee Titans", "Arizona Cardinals", "Atlanta Falcons", "Carolina Panthers" , "Chicago Bears", "Dallas Cowboys" , "Detroit Lions", "Green Bay Packers" , "Minnesota Vikings" , "New Orleans Saints" , "New York Giants" , "Philadelphia Eagles" , "San Francisco 49ers" , "Seattle Seahawks" , "St. Louis Rams", "Tampa Bay Buccaneers" , "Washington Redskins"};


    public String generateRandomTeamName(){
        if (existingTeams.size()>=teamNameList.length) throw new RuntimeException("No more random teams can be created");
        List<String> existingTeamsNameListTemp=new LinkedList<String>();
        for (Team currentExistingTeam: existingTeams){
            existingTeamsNameListTemp.add(currentExistingTeam.getName());
        }
        String teamNameTemp;
        while(existingTeamsNameListTemp.contains(teamNameTemp=teamNameList[random.nextInt(teamNameList.length)]));
        return teamNameTemp;
    }


    public int generateRandomYearOfFoundation(){
        return minFoundationYear + random.nextInt((new GregorianCalendar().get(Calendar.YEAR))-minFoundationYear);
    }


}

