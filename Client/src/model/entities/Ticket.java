package model.entities;

public class Ticket
{
  private int ticketID;
  private Bicycle bicycleSeat;
  private Seat seatId;
  private Train trainId;
  private MyDate departureTime;
  private MyDate arrivalTime;

  public Ticket(int ticketID, Bicycle bicycleSeat, Seat seatId, Train trainId,
      MyDate departureTime, MyDate arrivalTime)
  {
    this.ticketID = ticketID;
    this.bicycleSeat = bicycleSeat;
    this.seatId = seatId;
    this.trainId = trainId;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
  }


  public Ticket(int ticketID, Seat seatId)
  {
    this.ticketID = ticketID;
    this.seatId = seatId;
  }

  public Ticket(int ticketID, Bicycle bicycleSeat)
  {
    this.ticketID = ticketID;
    this.bicycleSeat = bicycleSeat;
  }

  public Ticket(int ticketID)
  {
    this.ticketID = ticketID;
  }

  public int getTicketID()
  {
    return ticketID;
  }

  public void setTicketID(int ticketID)
  {
    this.ticketID = ticketID;
  }

  public Seat getSeatId()
  {
    return seatId;
  }

  public void setSeatId(Seat seatId)
  {
    this.seatId = seatId;
  }

  public Bicycle getBicycleSeat()
  {
    return bicycleSeat;
  }

  public void setBicycleSeat(Bicycle bicycleSeat)
  {
    this.bicycleSeat = bicycleSeat;
  }
  public MyDate getDepartureTime()
  {
    return departureTime;
  }
  public void setDepartureTime(MyDate departureTime)
  {
    this.departureTime = departureTime;
  }
  public MyDate getArrivalTime()
  {
    return arrivalTime;
  }
  public void setArrivalTime(MyDate arrivalTime)
  {
    this.arrivalTime = arrivalTime;
  }

  public Train getTrainId()
  {
    return trainId;
  }

  public String toString()
  {
    return "Ticket ID: " + ticketID + ", Seat ID: " + seatId.getSeatId() +
        ", Departure Time: " + departureTime.toString() + ", Arrival Time: " + arrivalTime.toString();
  }
}
