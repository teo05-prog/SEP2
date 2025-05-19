package network.requestHandlers;

import com.google.gson.Gson;
import model.entities.Ticket;
import services.ticket.TicketService;

import java.util.List;

public class TicketsRequestHandler implements RequestHandler
{
  private final TicketService ticketService;
  private final Gson gson = new Gson();

  public TicketsRequestHandler(TicketService ticketService)
  {
    this.ticketService = ticketService;
  }

  @Override public Object handler(String action, Object payload) throws Exception
  {
    return switch (action)
    {
      case "createTicket" -> handleCreateTicket(payload);
      case "createSeatTicket" -> handleCreateSeatTicket(payload);
      case "createBicycleTicket" -> handleCreateBicycleTicket(payload);
      case "createSeatAndBicycleTicket" -> handleCreateSeatAndBicycleTicket(payload);
      case "updateTicket" -> handleUpdateTicket(payload);
      case "deleteTicket" -> handleDeleteTicket(payload);
      case "getAllTickets" -> handleGetAllTickets();
      case "getTicketById" -> handleGetTicketById(payload);
      default -> throw new IllegalArgumentException("Unknown action: " + action);
    };
  }

  private Ticket handleCreateTicket(Object payload)
  {
    Ticket ticket = gson.fromJson(gson.toJson(payload), Ticket.class);
    ticketService.createTicket(ticket.getTicketID(), ticket.getTrainId(), ticket.getScheduleId(), ticket.getEmail());
    return ticket;
  }

  private Ticket handleCreateSeatTicket(Object payload)
  {
    Ticket ticket = gson.fromJson(gson.toJson(payload), Ticket.class);
    ticketService.createSeatTicket(ticket.getTicketID(), ticket.getSeatId(), ticket.getTrainId(),
        ticket.getScheduleId(), ticket.getEmail());
    return ticket;
  }

  private Ticket handleCreateBicycleTicket(Object payload)
  {
    Ticket ticket = gson.fromJson(gson.toJson(payload), Ticket.class);
    ticketService.createBicycleTicket(ticket.getTicketID(), ticket.getBicycleSeat(), ticket.getTrainId(),
        ticket.getScheduleId(), ticket.getEmail());
    return ticket;
  }

  private Ticket handleCreateSeatAndBicycleTicket(Object payload)
  {
    Ticket ticket = gson.fromJson(gson.toJson(payload), Ticket.class);
    ticketService.createSeatAndBicycleTicket(ticket.getTicketID(), ticket.getBicycleSeat(), ticket.getSeatId(),
        ticket.getTrainId(), ticket.getScheduleId(), ticket.getEmail());
    return ticket;
  }

  private Ticket handleUpdateTicket(Object payload)
  {
    Ticket ticket = gson.fromJson(gson.toJson(payload), Ticket.class);
    ticketService.updateTicket(ticket);
    return ticket;
  }

  private Ticket handleDeleteTicket(Object payload)
  {
    Ticket ticket = gson.fromJson(gson.toJson(payload), Ticket.class);
    ticketService.deleteTicket(ticket.getTicketID());
    return ticket;
  }

  private List<Ticket> handleGetAllTickets()
  {
    return ticketService.getAllTickets();
  }

  private Ticket handleGetTicketById(Object payload)
  {
    int ticketId = gson.fromJson(gson.toJson(payload), Integer.class);
    return ticketService.getTicketById(ticketId);
  }
}
