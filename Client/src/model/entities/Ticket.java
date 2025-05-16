package model.entities;

public class Ticket
{
  private int ticketID;
  private Bicycle bicycleSeat;
  private Seat seatId;
  private Train trainId;
  private Schedule scheduleId;
  private String email;
  private int price;

  public Ticket(int ticketID, Bicycle bicycleSeat, Seat seatId, Train trainId, Schedule scheduleId, String email,
      int price)
  {
    this.ticketID = ticketID;
    this.bicycleSeat = bicycleSeat;
    this.seatId = seatId;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.email = email;
    this.price = price;
  }

  public Ticket(int ticketID, Bicycle bicycleSeat, Seat seatId, Train trainId, Schedule scheduleId, String email)
  {
    this.ticketID = ticketID;
    this.bicycleSeat = bicycleSeat;
    this.seatId = seatId;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.email = email;
  }

  public Ticket(int ticketID, Train trainId, Schedule scheduleId, Seat seatId, String email)
  {
    this.ticketID = ticketID;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.seatId = seatId;
    this.email = email;
  }

  public Ticket(int ticketID, Train trainId, Schedule scheduleId, Bicycle bicycleSeat, String email)
  {
    this.ticketID = ticketID;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.bicycleSeat = bicycleSeat;
    this.email = email;
  }

  public Ticket(int ticketID, Train trainId, Schedule scheduleId, String email)
  {
    this.ticketID = ticketID;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.email = email;
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

  public Train getTrainId()
  {
    return trainId;
  }

  public void setTrainId(Train trainId)
  {
    this.trainId = trainId;
  }

  public int getPrice()
  {
    return price;
  }

  public void setPrice(int price)
  {
    this.price = price;
  }

  public Schedule getScheduleId()
  {
    return scheduleId;
  }

  public void setScheduleId(Schedule scheduleId)
  {
    this.scheduleId = scheduleId;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String toString()
  {
    return "Ticket{" + "ticketID=" + ticketID + ", bicycleSeat=" + bicycleSeat + ", seatId=" + seatId + ", trainId="
        + trainId + "," + scheduleId.toString() + ", email=" + email + ", price=" + price + '}';
  }
}
