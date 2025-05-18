package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Ticket;
import services.ticket.TicketService;
import services.ticket.TicketServiceImpl;
import session.Session;

import java.sql.SQLException;
import java.util.List;

public class UpcomingDeparturesVM
{
  private ObservableList<Ticket> upcomingDepartures;
  private TicketService ticketService;
  private Session session;

  public UpcomingDeparturesVM()
  {
    try
    {
      this.ticketService = TicketServiceImpl.getInstance();
      this.upcomingDepartures = FXCollections.observableArrayList();
      this.session = Session.getInstance();
      loadUpcomingDepartures();
    }
    catch (SQLException e)
    {
      System.err.println("Error initializing UpcomingDeparturesVM: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void loadUpcomingDepartures()
  {
    try
    {
      // Get the current user's email from the session
      String userEmail = session.getUserEmail();

      if (userEmail != null && !userEmail.isEmpty())
      {
        // Get tickets for the current user
        List<Ticket> userTickets = ticketService.getTicketsByEmail(userEmail);

        // Clear and add to the observable list
        upcomingDepartures.clear();
        upcomingDepartures.addAll(userTickets);
      }
    }
    catch (Exception e)
    {
      System.err.println("Error loading user tickets: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public ObservableList<Ticket> getUpcomingDepartures()
  {
    return upcomingDepartures;
  }

  // refresh data when needed
  public void refreshData()
  {
    loadUpcomingDepartures();
  }
}
