package soccer.web;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soccer.data.TrainerRepository;
import soccer.entities.Trainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class TrainerRepositoryImpl implements TrainerRepository{
    List<Trainer> trainerList=new ArrayList<Trainer>();
    Trainer trainerTemp;
    String serializationFileName="SerializedTrainers";
    @Autowired
    SessionFactory sessionFactory;


    public TrainerRepositoryImpl() {
        deSerialiseList();
    }

    public List<Trainer> findTrainers(int max, int count) {
        return findTrainers(max,count,0);
    }

    //@Autowired
    //Factory factory;
    public List<Trainer> findTrainers(int max, int count, int startingAt) {
        //int currentSize=trainerList.size();
        //if (trainerList.size()<count){// some initial trainers can be added here
            //for (int i=0; i<count-currentSize;i++){
                //trainerTemp=factory.generateRandomTrainer();
                //save(trainerTemp);
            //}
        //}
        syncListWithDB();
        if (startingAt>0 && (startingAt+count)<trainerList.size()){
            return trainerList.subList(startingAt,startingAt+count);
        }
        return trainerList;
    }

    public Trainer findOne(String trainerId) {
        syncListWithDB();
        for(Trainer trainerCurrent: trainerList)
            if (trainerCurrent.getId().equals(trainerId.replace("%20"," ")))
                return trainerCurrent;
        return null;
    }

    public void save(Trainer trainer) {
        sessionFactory.getCurrentSession().persist(trainer);
        if (findOne(trainer.getId())!=null) {
           trainerList.remove(findOne(trainer.getId()));
        }
        trainerList.add(trainer);
        serialiseList();
    }

    private void deSerialiseList(){
        try{
            List<Trainer> serializedListOfTrainers;
            FileInputStream fis = new FileInputStream(serializationFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            serializedListOfTrainers = (List<Trainer>)ois.readObject();
            ois.close();
            if (serializedListOfTrainers!=null&&serializedListOfTrainers.size()>0)
                trainerList=serializedListOfTrainers;
        }
        catch(IOException e) {
            System.out.println("Exception during deserialization: " + e);
        }catch(ClassNotFoundException e) {
            System.out.println("Exception during deserialization: " + e);
        }
    }

    private void serialiseList(){
        try {
            FileOutputStream fos = new FileOutputStream(serializationFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(trainerList);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void syncListWithDB(){
        String hql = "FROM Trainer";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        List results = query.list();
        for (Object obj: results){
            if (!trainerList.contains(obj))
                trainerList.add((Trainer)obj);
        }
    }
}
