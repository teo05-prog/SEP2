package network.requestHandlers;

import com.google.gson.Gson;
import model.entities.Train;
import model.entities.TrainList;
import model.exceptions.ValidationException;
import services.admin.TrainService;
import utilities.Logger;
import utilities.LogLevel;

public class TrainsRequestHandler implements RequestHandler
{
  private final TrainService trainService;
  private final Logger logger;
  private final Gson gson = new Gson();

  public TrainsRequestHandler(TrainService trainService, Logger logger)
  {
    this.trainService = trainService;
    this.logger = logger;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    try
    {
      logger.log("TrainsRequestHandler: Processing action: " + action, LogLevel.INFO);

      return switch (action)
      {
        case "getAllTrains" -> handleGetAllTrains();
        case "getTrainById" -> handleGetTrainById(payload);
        case "createTrain" -> handleCreateTrain(payload);
        case "updateTrain" -> handleUpdateTrain(payload);
        case "deleteTrain" -> handleDeleteTrain(payload);
        default -> throw new ValidationException("Unknown action: " + action);
      };
    }
    catch (ValidationException e)
    {
      logger.log("TrainsRequestHandler: Validation error: " + e.getMessage(), LogLevel.WARNING);
      throw e;
    }
    catch (Exception e)
    {
      logger.log("TrainsRequestHandler: Unexpected error: " + e.getMessage(), LogLevel.ERROR);
      e.printStackTrace();
      throw new Exception("Failed to process train request: " + e.getMessage());
    }
  }

  private Object handleGetAllTrains()
  {
    try
    {
      TrainList trains = trainService.getAllTrains();
      logger.log("Returning " + trains.size() + " trains", LogLevel.INFO);
      return trains;
    }
    catch (Exception e)
    {
      logger.log("Error getting all trains: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to retrieve trains");
    }
  }

  private Object handleGetTrainById(Object payload) throws ValidationException
  {
    if (payload == null)
    {
      throw new ValidationException("Train ID is required");
    }

    try
    {
      int trainId = gson.fromJson(gson.toJson(payload), Integer.class);
      if (trainId <= 0)
      {
        throw new ValidationException("Invalid train ID: " + trainId);
      }

      Train train = trainService.getTrainById(trainId);
      if (train == null)
      {
        throw new ValidationException("Train not found with ID: " + trainId);
      }

      return train;
    }
    catch (NumberFormatException e)
    {
      throw new ValidationException("Invalid train ID format");
    }
    catch (Exception e)
    {
      logger.log("Error getting train by ID: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to retrieve train");
    }
  }

  private Object handleCreateTrain(Object payload) throws ValidationException
  {
    if (payload == null)
    {
      throw new ValidationException("Train data is required");
    }

    try
    {
      Train train;
      if (payload instanceof Train)
      {
        train = (Train) payload;
      }
      else
      {
        train = gson.fromJson(gson.toJson(payload), Train.class);
      }

      if (train == null)
      {
        throw new ValidationException("Invalid train data");
      }

      validateTrain(train);
      trainService.createTrain(train.getTrainId());
      logger.log("Created train with ID: " + train.getTrainId(), LogLevel.INFO);
      return train;
    }
    catch (ValidationException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      logger.log("Error creating train: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to create train");
    }
  }

  private Object handleUpdateTrain(Object payload) throws ValidationException
  {
    if (payload == null)
    {
      throw new ValidationException("Train data is required");
    }

    try
    {
      Train train;
      if (payload instanceof Train)
      {
        train = (Train) payload;
      }
      else
      {
        train = gson.fromJson(gson.toJson(payload), Train.class);
      }

      if (train == null || train.getTrainId() <= 0)
      {
        throw new ValidationException("Invalid train data or missing train ID");
      }

      validateTrain(train);
      trainService.updateTrain(train);
      logger.log("Updated train with ID: " + train.getTrainId(), LogLevel.INFO);
      return train;
    }
    catch (ValidationException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      logger.log("Error updating train: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to update train");
    }
  }

  private Object handleDeleteTrain(Object payload) throws ValidationException
  {
    if (payload == null)
    {
      throw new ValidationException("Train ID is required");
    }

    try
    {
      int trainId = gson.fromJson(gson.toJson(payload), Integer.class);
      if (trainId <= 0)
      {
        throw new ValidationException("Invalid train ID: " + trainId);
      }

      trainService.deleteTrain(trainId);
      logger.log("Deleted train with ID: " + trainId, LogLevel.INFO);
      return trainId;
    }
    catch (ValidationException e)
    {
      throw e;
    }
    catch (NumberFormatException e)
    {
      throw new ValidationException("Invalid train ID format");
    }
    catch (Exception e)
    {
      logger.log("Error deleting train: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to delete train");
    }
  }

  private void validateTrain(Train train) throws ValidationException
  {
    if (train == null)
    {
      throw new ValidationException("Train cannot be null");
    }
    if (train.getTrainId() < 0)
    {
      throw new ValidationException("Train ID cannot be negative");
    }
  }
}