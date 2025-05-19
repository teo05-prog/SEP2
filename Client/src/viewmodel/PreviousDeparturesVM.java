package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Ticket;
import services.ticket.TicketService;
import services.ticket.TicketServiceImpl;
import session.Session;

import java.sql.SQLException;

import model.entities.MyDate;

import java.util.List;
import java.util.stream.Collectors;

public class PreviousDeparturesVM
{
  private ObservableList<Ticket> previousDepartures;
  private TicketService ticketService;
  private Session session;

  public PreviousDeparturesVM()
  {
    try
    {
      this.ticketService = TicketServiceImpl.getInstance();
      this.previousDepartures = FXCollections.observableArrayList();
      this.session = Session.getInstance();
      loadPreviousDepartures();
    }
    catch (SQLException e)
    {
      System.err.println("Error initializing PreviousDeparturesVM: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void loadPreviousDepartures()
  {
    try
    {
      // Get the current user's email from the session
      String userEmail = session.getUserEmail();

      if (userEmail != null && !userEmail.isEmpty())
      {
        // Get tickets for the current user
        List<Ticket> userTickets = ticketService.getTicketsByEmail(userEmail);

        // Get current date for comparison
        MyDate today = MyDate.today();

        // Filter tickets to include only those with departure dates in the past
        List<Ticket> pastTickets = userTickets.stream().filter(ticket -> {
          // Check if ticket's departure date exists and is in the past
          if (ticket.getScheduleId().getDepartureDate() != null)
          {
            return ticket.getScheduleId().getDepartureDate().isBefore(today);
          }
          return false;
        }).collect(Collectors.toList());

        // Clear and add to the observable list
        previousDepartures.clear();
        previousDepartures.addAll(pastTickets);
      }
    }
    catch (Exception e)
    {
      System.err.println("Error loading user tickets: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public ObservableList<Ticket> getPreviousDepartures()
  {
    return previousDepartures;
  }

  // refresh data when needed
  public void refreshData()
  {
    loadPreviousDepartures();
  }
}