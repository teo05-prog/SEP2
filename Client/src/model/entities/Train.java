package model.entities;

public class Train
{
  private int trainId;
  private Schedule schedule;

  public Train(int trainId)
  {
    this.trainId = trainId;
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
