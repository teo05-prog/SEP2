package services.admin;

import model.entities.Schedule;

import java.util.List;

public interface ScheduleService
{
  void createSchedule(Schedule schedule);

  void deleteSchedule(Schedule schedule);

  void updateSchedule(Schedule schedule);

  Schedule getScheduleById(int scheduleId);

  List<Schedule> getAllSchedules();
}
