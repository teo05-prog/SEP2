package services.ticket;

import model.entities.*;
import persistance.ticket.TicketDAO;
import persistance.ticket.TicketPostgresDAO;

import java.sql.SQLException;
import java.util.List;

public class TicketServiceImpl implements TicketService
{
  private final TicketDAO ticketDAO;
  private static TicketServiceImpl instance;

  public TicketServiceImpl() throws SQLException
  {
    this.ticketDAO = TicketPostgresDAO.getInstance();
  }

  public static TicketServiceImpl getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new TicketServiceImpl();
    }
    return instance;
  }

  @Override public void createSeatAndBicycleTicket(int ticketID, Bicycle bicycleSeat, Seat seatId,
      Schedule scheduleId, String email)
  {
    validateTicketData(ticketID, scheduleId, email);
    if (bicycleSeat == null || seatId == null)
    {
      throw new IllegalArgumentException("Both seat and bicycle must be provided for seat and bicycle ticket");
    }

    ticketDAO.createSeatAndBicycleTicket(ticketID, bicycleSeat, seatId, scheduleId, email);
  }

  @Override public void createSeatTicket(int ticketID, Seat seatId, Schedule scheduleId, String email)
  {
    validateTicketData(ticketID, scheduleId, email);
    if (seatId == null)
    {
      throw new IllegalArgumentException("Seat must be provided for seat ticket");
    }

    ticketDAO.createSeatTicket(ticketID, seatId, scheduleId, email);
  }

  @Override public void createBicycleTicket(int ticketID, Bicycle bicycleSeat, Schedule scheduleId,
      String email)
  {
    validateTicketData(ticketID, scheduleId, email);
    if (bicycleSeat == null)
    {
      throw new IllegalArgumentException("Bicycle must be provided for bicycle ticket");
    }

    ticketDAO.createBicycleTicket(ticketID, bicycleSeat, scheduleId, email);
  }

  @Override public void createTicket(int ticketID, Schedule scheduleId, String email)
  {
    validateTicketData(ticketID, scheduleId, email);
    ticketDAO.createTicket(ticketID, scheduleId, email);
  }

  @Override public void updateTicket(Ticket ticket)
  {
    if (ticket == null)
    {
      throw new IllegalArgumentException("Ticket cannot be null");
    }
    if (ticket.getTicketID() <= 0)
    {
      throw new IllegalArgumentException("Invalid ticket ID");
    }

    // Check if the ticket exists before updating
    Ticket existingTicket = ticketDAO.getTicketById(ticket.getTicketID());
    if (existingTicket == null)
    {
      throw new IllegalArgumentException("Ticket with ID " + ticket.getTicketID() + " does not exist");
    }

    ticketDAO.updateTicket(ticket);
  }

  @Override public void deleteTicket(int ticketId)
  {
    if (ticketId <= 0)
    {
      throw new IllegalArgumentException("Invalid ticket ID");
    }

    // Check if the ticket exists before deleting
    Ticket existingTicket = ticketDAO.getTicketById(ticketId);
    if (existingTicket == null)
    {
      throw new IllegalArgumentException("Ticket with ID " + ticketId + " does not exist");
    }

    ticketDAO.deleteTicket(ticketId);
  }

  @Override public List<Ticket> getAllTickets()
  {
    List<Ticket> tickets = ticketDAO.getAllTickets();
    if (tickets == null)
    {
      return List.of(); // Return empty list instead of null
    }
    return tickets;
  }

  @Override public Ticket getTicketById(int ticketId)
  {
    if (ticketId <= 0)
    {
      throw new IllegalArgumentException("Invalid ticket ID");
    }

    return ticketDAO.getTicketById(ticketId);
  }

  @Override public List<Ticket> getTicketsByEmail(String email)
  {
    return ticketDAO.getTicketsByEmail(email);
  }

  private void validateTicketData(int ticketID, Schedule scheduleId, String email)
  {
    if (ticketID <= 0)
    {
      throw new IllegalArgumentException("Ticket ID must be positive");
    }
    if (scheduleId == null)
    {
      throw new IllegalArgumentException("Schedule cannot be null");
    }
    if (email == null || email.trim().isEmpty())
    {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }

    // Additional email validation - basic pattern check
    if (!isValidEmail(email))
    {
      throw new IllegalArgumentException("Invalid email format");
    }

    // Check if ticket ID already exists
    Ticket existingTicket = ticketDAO.getTicketById(ticketID);
    if (existingTicket != null)
    {
      throw new IllegalArgumentException("Ticket with ID " + ticketID + " already exists");
    }
  }

  private boolean isValidEmail(String email)
  {
    // Basic email validation - contains @ and at least one dot after @
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return email.matches(emailRegex);
  }
}
