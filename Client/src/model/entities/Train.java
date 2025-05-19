package model.entities;

import java.util.ArrayList;

public class Train
{
  private int trainId;
  private ArrayList<Carriage> carriages;
  private Schedule schedule;

  public Train(int trainId)
  {
    this.trainId = trainId;
    this.carriages = new ArrayList<>(3);
  }

  public int getTrainId()
  {
    return trainId;
  }

  public void setTrainId(int trainId)
  {
    this.trainId = trainId;
  }

  public Schedule getSchedule()
  {
    return schedule;
  }

  public void setSchedule(Schedule schedule)
  {
    this.schedule = schedule;
  }

  @Override
  public String toString()
  {
    if (schedule != null && schedule.getDepartureStation() != null && schedule.getArrivalStation() != null)
    {

      return String.format("Train ID: %d, From: %s, To: %s", trainId, schedule.getDepartureStation().getName(),
          schedule.getArrivalStation().getName());
    }
    else
    {
      return String.format("Train ID: %d, No schedule", trainId);
    }
  }
}
