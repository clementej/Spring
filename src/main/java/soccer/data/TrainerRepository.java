package soccer.data;

import soccer.entities.Trainer;

import java.util.List;

public interface TrainerRepository {
    public List<Trainer> findTrainers(int max, int count);
    List<Trainer> findTrainers(int max, int count,int startingAt);
    Trainer  findOne(String trainerId);
    public void save(Trainer trainer);
}
