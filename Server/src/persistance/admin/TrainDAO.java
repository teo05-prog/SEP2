package persistance.admin;

import model.entities.Train;
import model.entities.TrainList;

import java.util.List;

public interface TrainDAO
{
  public void createTrain(int trainId);

  public Train readTrainById(int trainId);

  public void deleteTrain(int trainId);

  public void updateTrain(Train train);

  public TrainList allTrains();
  List<Train> getAllTrains();
}
