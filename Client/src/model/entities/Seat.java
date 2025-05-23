package model.entities;

/**
 * Represents a seat in the train system.
 * Each seat has a unique identifier and can be either booked or available.
 * The class provides methods to manage the booking status of the seat.
 *
 * @author Yelyzaveta Tkachenko, Bianca Buzdugan
 */
public class Seat
{
  private int seatId;
  private boolean isBooked;

  /**
   * Constructs a new Seat with the specified seat ID.
   * The seat is initially set as available (not booked).
   *
   * @param seatId the unique identifier for this seat
   */
  public Seat(int seatId)
  {
    this.seatId = seatId;
    this.isBooked = false;
  }

  /**
   * Gets the seat ID.
   *
   * @return the unique identifier of this seat
   */
  public int getSeatId()
  {
    return seatId;
  }

  /**
   * Sets the seat ID.
   *
   * @param seatId the new unique identifier for this seat
   */
  public void setSeatId(int seatId)
  {
    this.seatId = seatId;
  }

  /**
   * Checks if the seat is currently booked.
   *
   * @return true if the seat is booked, false if it's available
   */
  public boolean isBooked()
  {
    return isBooked;
  }

  /**
   * Sets the seat status to booked.
   * This method directly sets the booking status to true without any validation.
   */
  public void setBooked()
  {
    isBooked = true;
  }

  /**
   * Sets the seat status to available.
   * This method directly sets the booking status to false, making the seat available for booking.
   */
  public void setAvailable()
  {
    isBooked = false;
  }

  /**
   * Attempts to book the seat.
   * If the seat is available, it will be marked as booked.
   * If the seat is already booked, a message will be printed to the console.
   */
  public void book()
  {
    if (!isBooked)
    {
      isBooked = true;
    }
    else
    {
      System.out.println("Seat " + seatId + " is already reserved.");
    }
  }

  /**
   * Returns a string representation of the Seat object.
   * The format includes the seat ID and its current booking status.
   *
   * @return a string representation of this Seat showing ID and booking status
   */
  @Override public String toString()
  {
    return "Seat ID: " + seatId + ", Booked: " + isBooked;
  }
}