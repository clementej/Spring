package soccer.web;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soccer.config.Factory;
import soccer.entities.Player;
import soccer.data.PlayerRepository;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class PlayerRepositoryImpl implements PlayerRepository{
    List<Player> playerList=new ArrayList<Player>();
    Player playerTemp;
    String serializationFileName="SerializedPlayers";

    @Autowired SessionFactory sessionFactory;


    public PlayerRepositoryImpl() {
        deSerialiseList();
    }

    public List<Player> findPlayers(int max, int count) throws IOException {
        return findPlayers(max,count,0);
    }

    @Autowired
    Factory factory;
    public List<Player> findPlayers(int max, int count, int startingAt) throws IOException {
        int currentSize=playerList.size();
        if (playerList.size()<count){ // some initial players can be added with this
            for (int i=0; i<count-currentSize;i++){
                playerTemp= factory.generateRandomPlayer();
                save(playerTemp);
            }
        }

        syncListWithDB();

        if (startingAt>0 && (startingAt+count)<playerList.size()){
            return playerList.subList(startingAt,startingAt+count);
        }
        return playerList;
    }

    public Player findOne(String playerId) {
        syncListWithDB();
         for(Player playerCurrent: playerList)
            if (playerCurrent.getId().equals(playerId.replace("%20"," ")))
                return playerCurrent;
        return null;
    }

    public Player save(Player player) {
        sessionFactory.getCurrentSession().persist(player);
        if (findOne(player.getId())!=null) {
            playerList.remove(findOne(player.getId()));
        }
        playerList.add(player);
        serialiseList();
        return player;
    }

    private void syncListWithDB(){
        String hql = "FROM Player";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List results = query.list();
        for (Object obj: results){
            if (!playerList.contains(obj))
                playerList.add((Player)obj);
        }
    }

    private void serialiseList(){
        try {
            FileOutputStream fos = new FileOutputStream(serializationFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playerList);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void deSerialiseList(){
        try{
            List<Player> serializedListOfPlayers;
            FileInputStream fis = new FileInputStream(serializationFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            serializedListOfPlayers = (List<Player>)ois.readObject();
            ois.close();
            if (serializedListOfPlayers!=null&&serializedListOfPlayers.size()>0)
                playerList=serializedListOfPlayers;
        }
        catch(IOException e) {
            System.out.println("Exception during deserialization: " + e);
        }catch(ClassNotFoundException e) {
            System.out.println("Exception during deserialization: " + e);
        }
    }
}
