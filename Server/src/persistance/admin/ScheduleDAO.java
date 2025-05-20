package persistance.admin;

import model.entities.Schedule;

import java.util.List;

public interface ScheduleDAO
{
  void createSchedule(Schedule schedule);
  void updateSchedule(Schedule schedule);
  void deleteSchedule(Schedule schedule);
  Schedule getScheduleById(int scheduleId);
  List<Schedule> getAllSchedules();
  void assignScheduleToTrain(int scheduleId, int trainId);
  void removeScheduleFromTrain(int scheduleId);
  List<Schedule> getSchedulesByTrainId(int trainId);
}
