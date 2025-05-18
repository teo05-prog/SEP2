package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Ticket;
import services.ticket.TicketService;
import services.ticket.TicketServiceImpl;
import session.Session;

import java.sql.SQLException;
import java.util.List;

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

        // Clear and add to the observable list
        previousDepartures.clear();
        previousDepartures.addAll(userTickets);
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
  public void refreshData() {
    loadPreviousDepartures();
  }
}
