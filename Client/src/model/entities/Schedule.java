package model.entities;

public class Schedule
{
  private int scheduleId;
  private Station departureStation;
  private Station arrivalStation;

  private MyDate departureDate;
  private MyDate arrivalDate;


  public Schedule(int scheduleId, Station departureStation, Station arrivalStation, MyDate departureDate, MyDate arrivalDate)
  {
    this.scheduleId = scheduleId;
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

  public int getScheduleId()
  {
    return scheduleId;
  }

  public void setScheduleId(int scheduleId)
  {
    this.scheduleId = scheduleId;
  }

  public String toString()
  {
    return "From: " + departureStation.getName() + ", To: " + arrivalStation.getName()
        + ", Departure: " + departureDate.toString() + ", Arrival: " + arrivalDate.toString();
  }
}
