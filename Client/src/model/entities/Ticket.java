package model.entities;

/**
 * Represents a ticket in the train system.
 * A ticket can include various combinations of reservations including regular seats,
 * bicycle seats, and is associated with a specific train schedule. The ticket is
 * linked to a traveller through their email address.
 * This class supports different types of bookings:
 * - Regular seat reservation
 * - Bicycle seat reservation
 * - Combined seat and bicycle reservation
 * - Basic ticket without specific seat assignments
 *
 * @author Teodora Stoicescu, Bianca Buzdugan, Yelyzaveta Tkachenko
 */
public class Ticket
{
  private int ticketID;
  private Bicycle bicycleSeat;
  private Seat seatId;
  private Train trainId;
  private Schedule scheduleId;
  private String email;

  /**
   * Constructs a complete ticket with both regular seat and bicycle seat reservations.
   * This constructor is used when a traveller books both a regular seat and a bicycle seat.
   *
   * @param ticketID    the unique identifier for this ticket
   * @param bicycleSeat the bicycle seat reserved for this ticket
   * @param seatId      the regular seat reserved for this ticket
   * @param trainId     the train associated with this ticket
   * @param scheduleId  the schedule associated with this ticket
   * @param email       the email address of the customer
   */
  public Ticket(int ticketID, Bicycle bicycleSeat, Seat seatId, Train trainId, Schedule scheduleId, String email)
  {
    this.ticketID = ticketID;
    this.bicycleSeat = bicycleSeat;
    this.seatId = seatId;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.email = email;
  }

  /**
   * Constructs a ticket with regular seat reservation only.
   * This constructor is used when a traveller books only a regular seat.
   *
   * @param ticketID   the unique identifier for this ticket
   * @param trainId    the train associated with this ticket
   * @param scheduleId the schedule associated with this ticket
   * @param seatId     the regular seat reserved for this ticket
   * @param email      the email address of the customer
   */
  public Ticket(int ticketID, Train trainId, Schedule scheduleId, Seat seatId, String email)
  {
    this.ticketID = ticketID;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.seatId = seatId;
    this.email = email;
  }

  /**
   * Constructs a ticket with bicycle seat reservation only.
   * This constructor is used when a traveller books only a bicycle seat.
   *
   * @param ticketID    the unique identifier for this ticket
   * @param trainId     the train associated with this ticket
   * @param scheduleId  the schedule associated with this ticket
   * @param bicycleSeat the bicycle seat reserved for this ticket
   * @param email       the email address of the customer
   */
  public Ticket(int ticketID, Train trainId, Schedule scheduleId, Bicycle bicycleSeat, String email)
  {
    this.ticketID = ticketID;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.bicycleSeat = bicycleSeat;
    this.email = email;
  }

  /**
   * Constructs a basic ticket without specific seat assignments.
   * This constructor is used for general ticket booking without seat reservations.
   *
   * @param ticketID   the unique identifier for this ticket
   * @param trainId    the train associated with this ticket
   * @param scheduleId the schedule associated with this ticket
   * @param email      the email address of the customer
   */
  public Ticket(int ticketID, Train trainId, Schedule scheduleId, String email)
  {
    this.ticketID = ticketID;
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.email = email;
  }

  /**
   * Constructs a minimal ticket with only an ID.
   * This constructor may be used for ticket lookup or initialization purposes.
   *
   * @param ticketID the unique identifier for this ticket
   */
  public Ticket(int ticketID)
  {
    this.ticketID = ticketID;
  }

  /**
   * Gets the ticket ID.
   *
   * @return the unique identifier of this ticket
   */
  public int getTicketID()
  {
    return ticketID;
  }

  /**
   * Sets the ticket ID.
   *
   * @param ticketID the new unique identifier for this ticket
   */
  public void setTicketID(int ticketID)
  {
    this.ticketID = ticketID;
  }

  /**
   * Gets the regular seat associated with this ticket.
   *
   * @return the seat object, or null if no regular seat is reserved
   */
  public Seat getSeatId()
  {
    return seatId;
  }

  /**
   * Sets the regular seat for this ticket.
   *
   * @param seatId the seat to associate with this ticket
   */
  public void setSeatId(Seat seatId)
  {
    this.seatId = seatId;
  }

  /**
   * Gets the bicycle seat associated with this ticket.
   *
   * @return the bicycle seat object, or null if no bicycle seat is reserved
   */
  public Bicycle getBicycleSeat()
  {
    return bicycleSeat;
  }

  /**
   * Sets the bicycle seat for this ticket.
   *
   * @param bicycleSeat the bicycle seat to associate with this ticket
   */
  public void setBicycleSeat(Bicycle bicycleSeat)
  {
    this.bicycleSeat = bicycleSeat;
  }

  /**
   * Gets the train associated with this ticket.
   *
   * @return the train object
   */
  public Train getTrainId()
  {
    return trainId;
  }

  /**
   * Sets the train for this ticket.
   *
   * @param trainId the train to associate with this ticket
   */
  public void setTrainId(Train trainId)
  {
    this.trainId = trainId;
  }

  /**
   * Gets the schedule associated with this ticket.
   *
   * @return the schedule object
   */
  public Schedule getScheduleId()
  {
    return scheduleId;
  }

  /**
   * Sets the schedule for this ticket.
   *
   * @param scheduleId the schedule to associate with this ticket
   */
  public void setScheduleId(Schedule scheduleId)
  {
    this.scheduleId = scheduleId;
  }

  /**
   * Gets the email address of the traveller who owns this ticket.
   *
   * @return the traveller's email address
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * Sets the email address for this ticket.
   *
   * @param email the traveller's email address
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * Returns a string representation of the Ticket object.
   * The format includes all ticket details including ID, seats, train, schedule, and traveller email.
   *
   * @return a comprehensive string representation of this ticket
   */
  public String toString()
  {
    return "Ticket{" + "ticketID=" + ticketID + ", bicycleSeat=" + bicycleSeat + ", seatId=" + seatId + ", trainId="
        + trainId + "," + scheduleId.toString() + ", email=" + email + '}';
  }
}