package network.requestHandlers;

import com.google.gson.Gson;
import dtos.AddScheduleDTO;
import model.entities.MyDate;
import model.entities.Schedule;
import model.entities.Station;
import network.exceptions.InvalidActionException;
import services.admin.ScheduleService;
import services.admin.TrainService;
import utilities.LogLevel;
import utilities.Logger;

import java.util.List;
import java.util.Map;

public class AddScheduleRequestHandler implements RequestHandler
{
  private final TrainService trainService;
  private final ScheduleService scheduleService;
  private final Logger logger;
  private final Gson gson;

  public AddScheduleRequestHandler(TrainService trainService, ScheduleService scheduleService, Logger logger)
  {
    this.trainService = trainService;
    this.scheduleService = scheduleService;
    this.logger = logger;
    this.gson = new Gson();
  }

  @Override
  public Object handler(String action, Object payload) throws Exception
  {
    return switch (action)
    {
      case "schedule" -> addSchedule(payload);
      case "getNextScheduleId" -> getNextScheduleId();
      default -> throw new InvalidActionException("addSchedule", action);
    };
  }

  private Object addSchedule(Object payload) throws Exception
  {
    try
    {
      String jsonPayload = gson.toJson(payload);
      AddScheduleDTO request = gson.fromJson(jsonPayload, AddScheduleDTO.class);

      logger.log("Adding new schedule with ID: " + request.getScheduleId(), LogLevel.INFO);

      // Create schedule
      Station departureStation = new Station(request.getDepartureStation());
      Station arrivalStation = new Station(request.getArrivalStation());

      MyDate departureDate = parseDateTime(request.getDepartureDate(), request.getDepartureTime());
      MyDate arrivalDate = parseDateTime(request.getArrivalDate(), request.getArrivalTime());

      Schedule schedule = new Schedule(0, departureStation, arrivalStation, departureDate, arrivalDate);
      scheduleService.createSchedule(schedule);

      logger.log("Successfully added schedule with ID: " + request.getScheduleId(), LogLevel.INFO);

      return Map.of("success", true, "message", "Schedule added successfully", "scheduleId", request.getScheduleId());
    }
    catch (Exception e)
    {
      logger.log("Error adding schedule: " + e.getMessage(), LogLevel.ERROR);
      throw new Exception("Failed to add schedule: " + e.getMessage());
    }
  }

  private Object getNextScheduleId() throws Exception
  {
    try
    {
      List<Schedule> allSchedules = scheduleService.getAllSchedules();
      int nextId = allSchedules.size() + 1;

      logger.log("Generated next schedule ID: " + nextId, LogLevel.INFO);

      return Map.of("nextScheduleId", nextId);
    }
    catch (Exception e)
    {
      logger.log("Error getting next schedule ID: " + e.getMessage(), LogLevel.ERROR);
      throw new Exception("Failed to get next schedule ID: " + e.getMessage());
    }
  }

  private MyDate parseDateTime(String dateStr, String timeStr)
  {
    // Parse date string (assuming format: yyyy-MM-dd)
    String[] dateParts = dateStr.split("-");
    int year = Integer.parseInt(dateParts[0]);
    int month = Integer.parseInt(dateParts[1]);
    int day = Integer.parseInt(dateParts[2]);

    // Parse time string (format: HH:mm)
    String[] timeParts = timeStr.split(":");
    int hour = Integer.parseInt(timeParts[0]);
    int minute = Integer.parseInt(timeParts[1]);

    MyDate myDate = new MyDate(day, month, year);
    myDate.setHour(hour);
    myDate.setMinute(minute);

    return myDate;
  }
}