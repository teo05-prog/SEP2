package model.entities;

import java.util.ArrayList;

public class Schedule
{
  private String trainID;
  private ArrayList<Station> stations;
  private ArrayList<MyDate> arrivalDate;
  private ArrayList<MyDate> departureDate;

  public Schedule(String trainID)
  {
    this.trainID = trainID;
    this.stations = new ArrayList<>();
    this.arrivalDate = new ArrayList<>();
    this.departureDate = new ArrayList<>();
  }

  public ArrayList<Station> getStations()
  {
    return stations;
  }

  public ArrayList<MyDate> getArrivalDates()
  {
    return arrivalDate;
  }

  public ArrayList<MyDate> getDepartureDates()
  {
    return departureDate;
  }

  public String getTrainID()
  {
    return trainID;
  }

  public String toString()
  {
    for (int i = 0; i < stations.size(); i++)
    {
      System.out.println("Station: " + stations.get(i).getName() + ", Arrival: " + arrivalDate.get(i) + ", Departure: "
          + departureDate.get(i));
    }
    return "";
  }
}
