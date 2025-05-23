package persistance.admin;

import model.entities.Train;
import model.entities.TrainList;

public interface TrainDAO
{
  void createTrain(int trainId);
  Train readTrainById(int trainId);
  void deleteTrain(int trainId);
  void updateTrain(Train train);
  TrainList allTrains();
}
