package network.requestHandlers;

import com.google.gson.Gson;
import dtos.AddScheduleDTO;
import model.entities.MyDate;
import model.entities.Schedule;
import model.entities.Station;
import network.exceptions.InvalidActionException;
import services.admin.ScheduleService;
import utilities.LogLevel;
import utilities.Logger;

import java.util.List;
import java.util.Map;

public class AddScheduleRequestHandler implements RequestHandler
{
  private final ScheduleService scheduleService;
  private final Logger logger;
  private final Gson gson;

  public AddScheduleRequestHandler(ScheduleService scheduleService, Logger logger)
  {
    this.scheduleService = scheduleService;
    this.logger = logger;
    this.gson = new Gson();
  }

  @Override public Object handler(String action, Object payload) throws Exception
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

      Station departureStation = new Station(request.getDepartureStation());
      Station arrivalStation = new Station(request.getArrivalStation());

      MyDate departureDate = parseDateTime(request.getDepartureDate(), request.getDepartureTime());
      MyDate arrivalDate = parseDateTime(request.getArrivalDate(), request.getArrivalTime());

      logger.log(
          "Parsed departure date: day=" + departureDate.getDay() + ", month=" + departureDate.getMonth() + ", year="
              + departureDate.getYear() + ", hour=" + departureDate.getHour() + ", minute=" + departureDate.getMinute(),
          LogLevel.INFO);

      logger.log("Parsed arrival date: day=" + arrivalDate.getDay() + ", month=" + arrivalDate.getMonth() + ", year="
              + arrivalDate.getYear() + ", hour=" + arrivalDate.getHour() + ", minute=" + arrivalDate.getMinute(),
          LogLevel.INFO);

      int scheduleId = request.getScheduleId();
      Schedule schedule = new Schedule(scheduleId, departureStation, arrivalStation, departureDate, arrivalDate);

      int trainId = request.getTrainId();
      logger.log("Train ID for this schedule: " + trainId, LogLevel.INFO);

      scheduleService.createSchedule(schedule);

      logger.log("Linking schedule " + scheduleId + " to train " + trainId, LogLevel.INFO);
      scheduleService.assignScheduleToTrain(scheduleId, trainId);

      logger.log("Successfully added schedule with ID: " + scheduleId, LogLevel.INFO);

      return Map.of("success", true, "message", "Schedule added successfully", "scheduleId", scheduleId);
    }
    catch (Exception e)
    {
      logger.log("Error adding schedule: " + e.getMessage(), LogLevel.ERROR);
      e.printStackTrace();
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
    try
    {
      String[] dateParts = dateStr.split("-");
      if (dateParts.length != 3)
      {
        throw new IllegalArgumentException("Invalid date format: " + dateStr + ", expected yyyy-MM-dd");
      }

      int year = Integer.parseInt(dateParts[0]);
      int month = Integer.parseInt(dateParts[1]);
      int day = Integer.parseInt(dateParts[2]);

      String[] timeParts = timeStr.split(":");
      if (timeParts.length != 2)
      {
        throw new IllegalArgumentException("Invalid time format: " + timeStr + ", expected HH:mm");
      }

      int hour = Integer.parseInt(timeParts[0]);
      int minute = Integer.parseInt(timeParts[1]);

      MyDate myDate = new MyDate(year, month, day);
      myDate.setHour(hour);
      myDate.setMinute(minute);
      return myDate;
    }
    catch (Exception e)
    {
      System.err.println("Error parsing date/time: " + e.getMessage());
      e.printStackTrace();
      throw new IllegalArgumentException("Failed to parse date/time: " + dateStr + " " + timeStr, e);
    }
  }
}