package network.requestHandlers;

import com.google.gson.Gson;
import model.entities.Schedule;
import model.entities.Train;
import exceptions.ValidationException;
import services.admin.ScheduleService;
import services.admin.TrainService;
import utilities.Logger;
import utilities.LogLevel;

public class ModifyRequestHandler implements RequestHandler
{
  private final TrainService trainService;
  private final ScheduleService scheduleService;
  private final Logger logger;
  private final Gson gson = new Gson();

  public ModifyRequestHandler(TrainService trainService, ScheduleService scheduleService, Logger logger)
  {
    this.trainService = trainService;
    this.scheduleService = scheduleService;
    this.logger = logger;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    try
    {
      switch (action)
      {
        case "updateTrain":
          return handleUpdateTrain(payload);
        case "updateSchedule":
          return handleUpdateSchedule(payload);
        default:
          logger.log("ModifyRequestHandler: Unknown action: " + action, LogLevel.WARNING);
          throw new ValidationException("Unknown action: " + action);
      }
    }
    catch (ValidationException e)
    {
      logger.log("ModifyRequestHandler: Validation error: " + e.getMessage(), LogLevel.WARNING);
      throw e;
    }
    catch (Exception e)
    {
      logger.log("ModifyRequestHandler: Exception handling request: " + e.getMessage(), LogLevel.ERROR);
      e.printStackTrace();
      throw new Exception("Failed to update train schedule. Please try again.");
    }
  }

  private Object handleUpdateTrain(Object payload) throws Exception
  {
    logger.log("ModifyRequestHandler: Handling updateTrain request", LogLevel.INFO);
    // Validate payload
    if (payload == null)
    {
      throw new ValidationException("No train data provided");
    }
    Train train;
    try
    {
      if (payload instanceof Train)
      {
        train = (Train) payload;
      }
      else
      {
        String json = gson.toJson(payload);
        train = gson.fromJson(json, Train.class);
      }
      if (train == null || train.getTrainId() <= 0)
      {
        throw new ValidationException("Invalid train data");
      }
    }
    catch (Exception e)
    {
      logger.log("Error parsing train data: " + e.getMessage(), LogLevel.ERROR);
      throw new ValidationException("Invalid train data format");
    }
    // Validate train data before updating
    validateTrain(train);
    try
    {
      logger.log("Updating train with ID: " + train.getTrainId(), LogLevel.INFO);
      trainService.updateTrain(train);
      return true;
    }
    catch (Exception e)
    {
      logger.log("Failed to update train: " + e.getMessage(), LogLevel.ERROR);
      e.printStackTrace();
      throw new Exception("Failed to update train: " + e.getMessage());
    }
  }

  private Object handleUpdateSchedule(Object payload) throws Exception
  {
    logger.log("ModifyRequestHandler: Handling updateSchedule request", LogLevel.INFO);
    // Validate payload
    if (payload == null)
    {
      throw new ValidationException("No schedule data provided");
    }
    Schedule schedule;
    try
    {
      if (payload instanceof Schedule)
      {
        schedule = (Schedule) payload;
      }
      else
      {
        String json = gson.toJson(payload);
        schedule = gson.fromJson(json, Schedule.class);
      }
      if (schedule == null || schedule.getScheduleId() <= 0)
      {
        throw new ValidationException("Invalid schedule data");
      }
    }
    catch (Exception e)
    {
      logger.log("Error parsing schedule data: " + e.getMessage(), LogLevel.ERROR);
      throw new ValidationException("Invalid schedule data format");
    }
    // Validate schedule data before updating
    validateSchedule(schedule);
    try
    {
      logger.log("Updating schedule with ID: " + schedule.getScheduleId(), LogLevel.INFO);
      scheduleService.updateSchedule(schedule);
      return true;
    }
    catch (Exception e)
    {
      logger.log("Failed to update schedule: " + e.getMessage(), LogLevel.ERROR);
      e.printStackTrace();
      throw new Exception("Failed to update schedule: " + e.getMessage());
    }
  }

  private void validateTrain(Train train) throws ValidationException
  {
    if (train.getTrainId() <= 0)
    {
      throw new ValidationException("Invalid train ID");
    }
  }

  private void validateSchedule(Schedule schedule) throws ValidationException
  {
    if (schedule.getScheduleId() <= 0)
    {
      throw new ValidationException("Invalid schedule ID");
    }
    if (schedule.getDepartureStation() == null || schedule.getArrivalStation() == null)
    {
      throw new ValidationException("Departure and arrival stations must be specified");
    }
    if (schedule.getDepartureDate() == null || schedule.getArrivalDate() == null)
    {
      throw new ValidationException("Departure and arrival dates must be specified");
    }
  }
}