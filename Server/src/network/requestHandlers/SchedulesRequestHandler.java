package network.requestHandlers;

import com.google.gson.Gson;
import model.entities.Schedule;
import services.admin.ScheduleService;

import java.util.List;

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
      case "createSchedule" -> handleCreateSchedule(payload);
      case "updateSchedule" -> handleUpdateSchedule(payload);
      case "deleteSchedule" -> handleDeleteSchedule(payload);
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
}