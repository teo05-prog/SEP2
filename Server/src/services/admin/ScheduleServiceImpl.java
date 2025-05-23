package services.admin;

import model.entities.Schedule;
import persistance.admin.ScheduleDAO;

import java.util.List;

public class ScheduleServiceImpl implements ScheduleService
{
  private final ScheduleDAO scheduleDAO;

  public ScheduleServiceImpl(ScheduleDAO scheduleDAO)
  {
    this.scheduleDAO = scheduleDAO;
  }

  @Override public void createSchedule(Schedule schedule)
  {
    scheduleDAO.createSchedule(schedule);
  }

  @Override public void deleteSchedule(Schedule schedule)
  {
    scheduleDAO.deleteSchedule(schedule);
  }

  @Override public void updateSchedule(Schedule schedule)
  {
    scheduleDAO.updateSchedule(schedule);
  }

  @Override public Schedule getScheduleById(int scheduleId)
  {
    return scheduleDAO.getScheduleById(scheduleId);
  }

  @Override public List<Schedule> getAllSchedules()
  {
    return scheduleDAO.getAllSchedules();
  }

  @Override public void assignScheduleToTrain(int scheduleId, int trainId)
  {
    scheduleDAO.assignScheduleToTrain(scheduleId, trainId);
  }

  @Override public void removeScheduleFromTrain(int scheduleId)
  {
    scheduleDAO.removeScheduleFromTrain(scheduleId);
  }

  @Override public List<Schedule> getSchedulesByTrainId(int trainId)
  {
    return scheduleDAO.getSchedulesByTrainId(trainId);
  }
}
