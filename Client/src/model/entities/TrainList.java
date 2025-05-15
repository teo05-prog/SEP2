package model.entities;

import java.util.ArrayList;
import java.util.List;

public class TrainList
{
  private ArrayList<Train> trains;

  public TrainList()
  {
    this.trains = new ArrayList<>();
  }

  public TrainList(List<Train> trains)
  {
    this.trains = new ArrayList<>(trains);
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
    if (trains.isEmpty())
    {
      return "No trains available";
    }

    StringBuilder sb = new StringBuilder();
    for (Train train : trains)
    {
      sb.append(train.toString()).append("\n");
    }
    return sb.toString();
  }
}
