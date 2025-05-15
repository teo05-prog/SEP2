package network.requestHandlers;

import com.google.gson.Gson;
import model.entities.Train;
import model.entities.TrainList;
import services.admin.TrainService;

public class TrainsRequestHandler implements RequestHandler
{
  private final TrainService trainService;
  private final Gson gson = new Gson();

  public TrainsRequestHandler(TrainService trainService)
  {
    this.trainService = trainService;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    return switch (action)
    {
      case "getAllTrains" -> handleGetAllTrains(payload);
      case "getTrainById" -> handleGetTrainById(payload);
      case "createTrain" -> handleCreateTrain(payload);
      case "updateTrain" -> handleUpdateTrain(payload);
      case "deleteTrain" -> handleDeleteTrain(payload);
      default -> throw new IllegalArgumentException("Unknown action: " + action);
    };
  }

  private Object handleGetAllTrains(Object payload)
  {
    TrainList trains = trainService.getAllTrains();
    System.out.println("Returning " + trains.size() + " trains");
    return trains;
  }

  private Object handleGetTrainById(Object payload)
  {
    int trainId = gson.fromJson(gson.toJson(payload), Integer.class);
    return trainService.getTrainById(trainId);
  }

  private Object handleCreateTrain(Object payload)
  {
    int trainId = gson.fromJson(gson.toJson(payload), Integer.class);
    trainService.createTrain(trainId);
    return trainId;
  }

  private Object handleUpdateTrain(Object payload)
  {
    Train train = gson.fromJson(gson.toJson(payload), Train.class);
    trainService.updateTrain(train);
    return train;
  }

  private Object handleDeleteTrain(Object payload)
  {
    int trainId = gson.fromJson(gson.toJson(payload), Integer.class);
    trainService.deleteTrain(trainId);
    return trainId;
  }
}
