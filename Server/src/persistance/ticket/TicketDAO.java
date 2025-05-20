package persistance.ticket;

import model.entities.*;

import java.util.List;

public interface TicketDAO
{
  void createSeatAndBicycleTicket(int ticketID, Bicycle bicycleSeat, Seat seatId, Schedule scheduleId, String email);
  void createSeatTicket(int ticketID, Seat seatId, Schedule scheduleId, String email);
  void createBicycleTicket(int ticketID, Bicycle bicycleSeat, Schedule scheduleId, String email);
  void createTicket(int ticketID, Schedule scheduleId, String email);
  void updateTicket(Ticket ticket);
  void deleteTicket(int ticketId);
  List<Ticket> getAllTickets();
  Ticket getTicketById(int ticketId);
  List<Ticket> getTicketsByEmail(String email);
}
