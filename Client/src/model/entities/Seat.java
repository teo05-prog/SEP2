package model.entities;

public class Seat
{
  private int seatId;
  private boolean isBooked;

  public Seat(int seatId)
  {
    this.seatId = seatId;
    this.isBooked = false;
  }

  public int getSeatId()
  {
    return seatId;
  }

  public void setSeatId(int seatId)
  {
    this.seatId = seatId;
  }

  public boolean isBooked()
  {
    return isBooked;
  }

  public void setBooked()
  {
    isBooked = true;
  }

  public void setAvailable()
  {
    isBooked = false;
  }

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

  @Override public String toString()
  {
    return "Seat ID: " + seatId + ", Booked: " + isBooked;
  }
}
