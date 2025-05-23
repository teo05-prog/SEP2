package model.entities;

/**
 * Represents a bicycle seat in the train system.
 * Each bicycle has a unique seat ID and can be booked or available for use.
 * The class provides methods to manage the booking status and retrieve bicycle information.
 *
 * @author Yelyzaveta Tkachenko
 */
public class Bicycle
{
  private int bicycleSeatId;
  private boolean isBooked;

  /**
   * Constructs a new Bicycle with the specified seat ID.
   * The bicycle is initially set as available (not booked).
   *
   * @param bicycleSeatId the unique identifier for this bicycle seat
   */
  public Bicycle(int bicycleSeatId)
  {
    this.bicycleSeatId = bicycleSeatId;
    this.isBooked = false;
  }

  /**
   * Gets the bicycle seat ID.
   *
   * @return the unique identifier of this bicycle seat
   */
  public int getBicycleSeatId()
  {
    return bicycleSeatId;
  }

  /**
   * Sets the bicycle seat ID.
   *
   * @param bicycleSeatId the new unique identifier for this bicycle seat
   */
  public void setBicycleSeatId(int bicycleSeatId)
  {
    this.bicycleSeatId = bicycleSeatId;
  }

  /**
   * Checks if the bicycle seat is currently booked.
   *
   * @return true if the bicycle seat is booked, false if it's available
   */
  public boolean isBooked()
  {
    return isBooked;
  }

  /**
   * This method directly sets the booking status to true without any validation.
   */
  public void setBooked()
  {
    isBooked = true;
  }

  /**
   * This method directly sets the booking status to false, making the bicycle available for booking.
   */
  public void setAvailable()
  {
    isBooked = false;
  }

  /**
   * Attempts to book the bicycle.
   * If the bicycle seat is available, it will be marked as booked.
   * If the bicycle seat is already booked, a message will be printed to the console.
   */
  public void book()
  {
    if (!isBooked)
    {
      isBooked = true;
    }
    else
    {
      System.out.println("Bicycle seat " + bicycleSeatId + " is already reserved.");
    }
  }

  /**
   * Returns a string representation of the Bicycle object.
   * The format includes the bicycle seat ID and its current booking status.
   *
   * @return a string representation of this Bicycle showing seat ID and reservation status
   */
  @Override public String toString()
  {
    return "Bicycle Seat ID: " + bicycleSeatId + ", Reserved: " + isBooked;
  }
}