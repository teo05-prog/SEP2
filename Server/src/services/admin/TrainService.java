package services.admin;

import model.entities.Train;
import model.entities.TrainList;

public interface TrainService
{
  void createTrain(int trainId);
  void deleteTrain(int trainId);
  TrainList getAllTrains();
  Train getTrainById(int trainId);
  void updateTrain(Train train);
}
