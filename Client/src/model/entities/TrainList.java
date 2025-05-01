package model.entities;

import java.util.ArrayList;

public class TrainList
{
  private ArrayList<Train> trains;

  public TrainList()
  {
    this.trains = new ArrayList<>();
  }

  public ArrayList<Train> getTrains()
  {
    return trains;
  }

  public void addTrain(Train train)
  {
    this.trains.add(train);
  }

  public void removeTrain(Train train)
  {
    this.trains.remove(train);
  }

  public String toString()
  {
    for (Train train : trains)
    {
      return train.toString();
    }
    return "";
  }
}
