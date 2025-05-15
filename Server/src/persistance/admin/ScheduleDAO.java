package persistance.admin;

import model.entities.Schedule;

import java.util.List;

public interface ScheduleDAO
{
  List<Schedule> getAllSchedules();
  Schedule getScheduleById(int scheduleId);
  void createSchedule(Schedule schedule);
  void updateSchedule(Schedule schedule);
  void deleteSchedule(Schedule schedule);
}
