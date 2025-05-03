package model.entities;

public class Schedule
{
  private Station departureStation;
  private Station arrivalStation;

  private MyDate departureDate;
  private MyDate arrivalDate;

  public Schedule(Station departureStation, Station arrivalStation, MyDate departureDate, MyDate arrivalDate)
  {
    this.departureStation = departureStation;
    this.arrivalStation = arrivalStation;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
  }

  public MyDate getDepartureDate()
  {
    return departureDate;
  }

  public void setDepartureDate(MyDate departureDate)
  {
    this.departureDate = departureDate;
  }

  public MyDate getArrivalDate()
  {
    return arrivalDate;
  }

  public void setArrivalDate(MyDate arrivalDate)
  {
    this.arrivalDate = arrivalDate;
  }

  public Station getDepartureStation()
  {
    return departureStation;
  }

  public void setDepartureStation(Station departureStation)
  {
    this.departureStation = departureStation;
  }

  public Station getArrivalStation()
  {
    return arrivalStation;
  }

  public void setArrivalStation(Station arrivalStation)
  {
    this.arrivalStation = arrivalStation;
  }

  public String toString()
  {
    return "Departure Station: " + departureStation.getName() + ", Arrival Station: " + arrivalStation.getName()
        + ", Departure Date: " + departureDate.toString() + ", Arrival Date: " + arrivalDate.toString();
  }
}
