package network.requestHandlers;

import com.google.gson.Gson;
import model.entities.Schedule;
import services.admin.ScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchedulesRequestHandler implements RequestHandler
{
  private final ScheduleService scheduleService;
  private final Gson gson = new Gson();

  public SchedulesRequestHandler(ScheduleService scheduleService)
  {
    this.scheduleService = scheduleService;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    return switch (action)
    {
      case "getAllSchedules" -> handleGetAllSchedules();
      case "getScheduleById" -> handleGetScheduleById(payload);
      case "getSchedulesByTrainId" -> handleGetSchedulesByTrainId(payload);
      case "createSchedule" -> handleCreateSchedule(payload);
      case "updateSchedule" -> handleUpdateSchedule(payload);
      case "deleteSchedule" -> handleDeleteSchedule(payload);
      case "assignScheduleToTrain" -> handleAssignScheduleToTrain(payload);
      case "removeScheduleFromTrain" -> handleRemoveScheduleFromTrain(payload);
      default -> throw new IllegalArgumentException("Unknown action: " + action);
    };
  }

  private List<Schedule> handleGetAllSchedules()
  {
    return scheduleService.getAllSchedules();
  }

  private Schedule handleGetScheduleById(Object payload)
  {
    int scheduleId = gson.fromJson(gson.toJson(payload), Integer.class);
    return scheduleService.getScheduleById(scheduleId);
  }

  private Schedule handleCreateSchedule(Object payload)
  {
    Schedule schedule = gson.fromJson(gson.toJson(payload), Schedule.class);
    scheduleService.createSchedule(schedule);
    return schedule;
  }

  private Schedule handleUpdateSchedule(Object payload)
  {
    Schedule schedule = gson.fromJson(gson.toJson(payload), Schedule.class);
    scheduleService.updateSchedule(schedule);
    return schedule;
  }

  private Schedule handleDeleteSchedule(Object payload)
  {
    Schedule schedule = gson.fromJson(gson.toJson(payload), Schedule.class);
    scheduleService.deleteSchedule(schedule);
    return schedule;
  }

  private Boolean handleAssignScheduleToTrain(Object payload)
  {
    @SuppressWarnings("unchecked") Map<String, Integer> data = gson.fromJson(gson.toJson(payload), Map.class);
    int scheduleId = data.get("scheduleId");
    int trainId = data.get("trainId");

    scheduleService.assignScheduleToTrain(scheduleId, trainId);
    return true;
  }

  private Boolean handleRemoveScheduleFromTrain(Object payload)
  {
    int scheduleId = gson.fromJson(gson.toJson(payload), Integer.class);
    scheduleService.removeScheduleFromTrain(scheduleId);
    return true;
  }

  private List<Schedule> handleGetSchedulesByTrainId(Object payload)
  {
    try
    {
      Integer trainId = null;
      if (payload instanceof Integer)
      {
        trainId = (Integer) payload;
      }
      else
      {
        trainId = gson.fromJson(gson.toJson(payload), Integer.class);
      }
      if (trainId == null)
      {
        throw new IllegalArgumentException("Invalid train ID: null");
      }
      List<Schedule> schedules = scheduleService.getSchedulesByTrainId(trainId);
      return schedules != null ? schedules : new ArrayList<>();
    }
    catch (Exception e)
    {
      System.err.println("Error in handleGetSchedulesByTrainId: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }
}