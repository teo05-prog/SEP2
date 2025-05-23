package model.entities;

/**
 * Represents a schedule in the train system.
 * A schedule defines a journey between two stations with specific departure and arrival times.
 * It includes information about available seats and provides methods to manage schedule details.
 * Each schedule has a unique identifier and connects a departure station to an arrival station
 * with associated departure and arrival dates/times.
 *
 * @author Yelyzaveta Tkachenko, Bianca Buzdugan, Teodora Stoicescu
 */
public class Schedule
{
  private int scheduleId;
  private Station departureStation;
  private Station arrivalStation;
  private MyDate departureDate;
  private MyDate arrivalDate;
  private int availableSeats;

  /**
   * Constructs a new Schedule with the specified details.
   *
   * @param scheduleId       the unique identifier for this schedule
   * @param departureStation the station where the journey begins
   * @param arrivalStation   the station where the journey ends
   * @param departureDate    the date and time when the journey departs
   * @param arrivalDate      the date and time when the journey arrives
   */
  public Schedule(int scheduleId, Station departureStation, Station arrivalStation, MyDate departureDate,
      MyDate arrivalDate)
  {
    this.scheduleId = scheduleId;
    this.departureStation = departureStation;
    this.arrivalStation = arrivalStation;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
  }

  /**
   * Gets the departure date and time for this schedule.
   *
   * @return the departure date and time
   */
  public MyDate getDepartureDate()
  {
    return departureDate;
  }

  /**
   * Sets the departure date and time for this schedule.
   *
   * @param departureDate the new departure date and time
   */
  public void setDepartureDate(MyDate departureDate)
  {
    this.departureDate = departureDate;
  }

  /**
   * Gets the arrival date and time for this schedule.
   *
   * @return the arrival date and time
   */
  public MyDate getArrivalDate()
  {
    return arrivalDate;
  }

  /**
   * Sets the arrival date and time for this schedule.
   *
   * @param arrivalDate the new arrival date and time
   */
  public void setArrivalDate(MyDate arrivalDate)
  {
    this.arrivalDate = arrivalDate;
  }

  /**
   * Gets the departure station for this schedule.
   *
   * @return the station where the journey begins
   */
  public Station getDepartureStation()
  {
    return departureStation;
  }

  /**
   * Sets the departure station for this schedule.
   *
   * @param departureStation the new departure station
   */
  public void setDepartureStation(Station departureStation)
  {
    this.departureStation = departureStation;
  }

  /**
   * Gets the arrival station for this schedule.
   *
   * @return the station where the journey ends
   */
  public Station getArrivalStation()
  {
    return arrivalStation;
  }

  /**
   * Sets the arrival station for this schedule.
   *
   * @param arrivalStation the new arrival station
   */
  public void setArrivalStation(Station arrivalStation)
  {
    this.arrivalStation = arrivalStation;
  }

  /**
   * Gets the unique identifier for this schedule.
   *
   * @return the schedule ID
   */
  public int getScheduleId()
  {
    return scheduleId;
  }

  /**
   * Sets the unique identifier for this schedule.
   *
   * @param scheduleId the new schedule ID
   */
  public void setScheduleId(int scheduleId)
  {
    this.scheduleId = scheduleId;
  }

  /**
   * Gets the number of available seats for this schedule.
   *
   * @return the number of seats available for booking
   */
  public int getAvailableSeats()
  {
    return availableSeats;
  }

  /**
   * Sets the number of available seats for this schedule.
   *
   * @param availableSeats the new number of available seats
   */
  public void setAvailableSeats(int availableSeats)
  {
    this.availableSeats = availableSeats;
  }

  /**
   * Returns a string representation of this Schedule.
   * The format includes departure station, arrival station, departure date, and arrival date.
   *
   * @return a string representation of this schedule showing key journey details
   */
  public String toString()
  {
    return "From: " + departureStation.getName() + ", To: " + arrivalStation.getName() + ", Departure: "
        + departureDate.toString() + ", Arrival: " + arrivalDate.toString();
  }
}