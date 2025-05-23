package network.requestHandlers;

import com.google.gson.Gson;
import dtos.AddTrainDTO;
import model.entities.Train;
import model.entities.TrainList;
import network.exceptions.InvalidActionException;
import services.admin.TrainService;
import utilities.LogLevel;
import utilities.Logger;

import java.util.Map;

public class AddTrainRequestHandler implements RequestHandler
{
  private final TrainService trainService;
  private final Logger logger;
  private final Gson gson;

  public AddTrainRequestHandler(TrainService trainService, Logger logger)
  {
    this.trainService = trainService;
    this.logger = logger;
    this.gson = new Gson();
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    return switch (action)
    {
      case "train" -> addTrain(payload);
      case "getNextTrainId" -> getNextTrainId();
      default -> throw new InvalidActionException("addTrain", action);
    };
  }

  private Object addTrain(Object payload) throws Exception
  {
    try
    {
      String jsonPayload = gson.toJson(payload);
      AddTrainDTO request = gson.fromJson(jsonPayload, AddTrainDTO.class);

      int trainId = request.getTrainId();
      trainService.createTrain(trainId);

      logger.log("Adding new train with ID: " + trainId, LogLevel.INFO);

      return Map.of("success", true, "message", "Train added successfully", "trainId", trainId);
    }
    catch (Exception e)
    {
      logger.log("Error adding train: " + e.getMessage(), LogLevel.ERROR);
      throw new Exception("Failed to add train: " + e.getMessage());
    }
  }

  private Object getNextTrainId() throws Exception
  {
    try
    {
      TrainList allTrains = trainService.getAllTrains();
      int nextId = allTrains.size() + 1;

      logger.log("Generated next train ID: " + nextId, LogLevel.INFO);

      return Map.of("nextTrainId", nextId);
    }
    catch (Exception e)
    {
      logger.log("Error getting next train ID: " + e.getMessage(), LogLevel.ERROR);
      throw new Exception("Failed to get next train ID: " + e.getMessage());
    }
  }
}
