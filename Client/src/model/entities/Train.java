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

  public String toString()
  {
    return "Train ID: " + trainId + ", " + schedule.toString();
  }
}
