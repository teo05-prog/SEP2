package model.entities;

public class Ticket
{
  private int ticketID;
  private String type;
  private Seat seatId;
  public Ticket(int ticketID, String type, Seat seatId)
  {
    this.ticketID = ticketID;
    this.type = type;
    this.seatId = seatId;

  }
  public int getTicketID()
  {
    return ticketID;
  }
  public void setTicketID(int ticketID)
  {
    this.ticketID = ticketID;
  }
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public Seat getSeatId()
  {
    return seatId;
  }
  public String toString()
  {
    return "Ticket ID: " + ticketID + ", Type: " + type + ", Seat ID: " + seatId.getSeatId();
  }

}
