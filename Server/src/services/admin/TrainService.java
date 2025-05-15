package services.admin;

import model.entities.Train;

import java.util.List;

public interface TrainService
{
  void createTrain(int trainId);
  void deleteTrain(int trainId);
  List<Train> getAllTrains();
  Object getTrainById(int trainId);
  void updateTrain(Train train);
}
