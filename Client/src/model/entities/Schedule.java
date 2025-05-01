package model.entities;

import java.util.ArrayList;

public class Schedule
{
  private Station departureStation;
  private Station arrivalStation;

  private MyDate departureDate;
  private MyDate arrivalDate;

  private ArrayList<Seat> seats;

  public Schedule(Station departureStation, Station arrivalStation, MyDate departureDate, MyDate arrivalDate)
  {
    this.departureStation = departureStation;
    this.arrivalStation = arrivalStation;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
    this.seats = new ArrayList<>();
  }

  public MyDate getDepartureDate()
  {
    return departureDate;
  }

  public MyDate getArrivalDate()
  {
    return arrivalDate;
  }

  public Station getDepartureStation()
  {
    return departureStation;
  }

  public Station getArrivalStation()
  {
    return arrivalStation;
  }

  public ArrayList<Seat> getSeats()
  {
    return seats;
  }

  public int getNumberOfSeats()
  {
    return seats.size();
  }

  public void setNumberOfSeats(int seats)
  {
    if (seats > 16)
    {
      throw new IllegalArgumentException("Number of seats cannot exceed 16");
    }
    this.seats = new ArrayList<>(seats);
  }

  public String toString()
  {
    return "Departure Station: " + departureStation.getName() + ", Arrival Station: " + arrivalStation.getName()
        + ", Departure Date: " + departureDate.toString() + ", Arrival Date: " + arrivalDate.toString()
        + ", Number of Seats: " + seats.size();
  }
}
