package services.admin;

import model.entities.Train;
import model.entities.TrainList;
import persistance.admin.TrainDAO;

public class TrainServiceImpl implements TrainService
{
  private final TrainDAO trainDAO;

  public TrainServiceImpl(TrainDAO trainDAO)
  {
    this.trainDAO = trainDAO;
  }

  @Override public void createTrain(int trainId)
  {
    Train train = new Train(trainId);
    trainDAO.createTrain(train.getTrainId());

    TrainList trainList = new TrainList();
    trainList.addTrain(train);
  }

  @Override public void deleteTrain(int trainId)
  {
    Train train = new Train(trainId);
    trainDAO.deleteTrain(train.getTrainId());

    TrainList trainList = new TrainList();
    trainList.removeTrain(train);
  }

  @Override public TrainList getAllTrains()
  {
    TrainList trains = trainDAO.allTrains();
    System.out.println("Returning " + trains.size() + " trains");
    return trains;
  }

  @Override public Train getTrainById(int trainId)
  {
    Train train = trainDAO.readTrainById(trainId);
    if (train == null)
    {
      System.out.println("Train with ID " + trainId + " not found");
      return null;
    }
    return train;
  }

  @Override public void updateTrain(Train train)
  {
    Train existingTrain = trainDAO.readTrainById(train.getTrainId());
    if (existingTrain == null)
    {
      System.out.println("Train with ID " + train.getTrainId() + " not found");
      return;
    }
    trainDAO.updateTrain(train);
  }
}
