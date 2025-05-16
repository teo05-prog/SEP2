package services.ticket;

import model.entities.*;

import java.util.List;

public interface TicketService
{
  void createSeatAndBicycleTicket(int ticketID, Bicycle bicycleSeat, Seat seatId, Train trainId, Schedule scheduleId, String email);
  void createSeatTicket(int ticketID, Seat seatId, Train trainId, Schedule scheduleId, String email);
  void createBicycleTicket(int ticketID, Bicycle bicycleSeat, Train trainId, Schedule scheduleId, String email);
  void createTicket(int ticketID, Train trainId, Schedule scheduleId, String email);
  void updateTicket(Ticket ticket);
  void deleteTicket(int ticketId);
  List<Ticket> getAllTickets();
  Ticket getTicketById(int ticketId);
}
