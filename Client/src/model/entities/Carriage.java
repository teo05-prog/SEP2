package model.entities;

import java.util.ArrayList;

public class Carriage
{
  private int carriageId;
  private ArrayList<Seat> seats;
  private ArrayList<Bicycle> bicycles;

  public Carriage(int carriageId)
  {
    this.carriageId = carriageId;
    int seatCount = 16;
    this.seats = new ArrayList<>(seatCount);
    int bicycleCount = 2;
    this.bicycles = new ArrayList<>(bicycleCount);
  }

  public int getCarriageId()
  {
    return carriageId;
  }

  public void setCarriageId(int carriageId)
  {
    this.carriageId = carriageId;
  }

  public void bookedSeat(int seatId)
  {
    for (int i = 0; i < seats.size(); i++)
    {
      if (seats.get(i).getSeatId() == seatId)
      {
        seats.get(i).setBooked();
      }
    }
  }

  public void availableSeat(int seatId)
  {
    for (int i = 0; i < seats.size(); i++)
    {
      if (seats.get(i).getSeatId() == seatId)
      {
        seats.get(i).setAvailable();
      }
    }
  }

  public ArrayList<Seat> getSeats()
  {
    return seats;
  }

  public void bookedBicycle(int bicycleId)
  {
    for (int i = 0; i < bicycles.size(); i++)
    {
      if (bicycles.get(i).getBicycleSeatId() == bicycleId && !bicycles.get(i).isBooked()) // make Observer
      {
        bicycles.get(i).setBooked();
      }
    }
  }

  public void availableBicycle(int bicycleId)
  {
    for (int i = 0; i < bicycles.size(); i++)
    {
      if (bicycles.get(i).getBicycleSeatId() == bicycleId && bicycles.get(i).isBooked())
      {
        bicycles.get(i).setAvailable();
      }
    }
  }

  public ArrayList<Bicycle> getBicycles()
  {
    return bicycles;
  }

  public String toString()
  {
    String str = "";
    str += "Carriage ID: " + carriageId + "\n";
    for (int i = 0; i < seats.size(); i++)
    {
      str += "Seat ID: " + seats.get(i).getSeatId() + ", Reserved: " + seats.get(i).isBooked() + "\n";
    }
    for (int i = 0; i < bicycles.size(); i++)
    {
      str += "Bicycle ID: " + bicycles.get(i).getBicycleSeatId() + ", Reserved: " + bicycles.get(i).isBooked() + "\n";
    }
    return str;
  }

}
