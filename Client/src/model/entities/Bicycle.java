package model.entities;

public class Bicycle
{
  private int bicycleSeatId;
  private boolean isBooked;

  public Bicycle(int bicycleSeatId)
  {
    this.bicycleSeatId = bicycleSeatId;
    this.isBooked = false;
  }

  public int getBicycleSeatId()
  {
    return bicycleSeatId;
  }

  public void setBicycleSeatId(int bicycleSeatId)
  {
    this.bicycleSeatId = bicycleSeatId;
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
      System.out.println("Bicycle seat " + bicycleSeatId + " is already reserved.");
    }
  }

  @Override public String toString()
  {
    return "Bicycle Seat ID: " + bicycleSeatId + ", Reserved: " + isBooked;
  }
}
