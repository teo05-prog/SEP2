package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.MyDate;
import model.entities.Ticket;
import services.ticket.TicketService;
import services.ticket.TicketServiceImpl;
import session.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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
        // Get current date for comparison
        MyDate today = MyDate.today();
        // Filter tickets to include only those with upcoming departure dates
        List<Ticket> futureTickets = userTickets.stream().filter(ticket -> {
          // Check if ticket's departure date exists and is today or in the future
          if (ticket.getScheduleId().getDepartureDate() != null)
          {
            MyDate departureDate = ticket.getScheduleId().getDepartureDate();
            // Keep the ticket if today's date is before or equal to the departure date
            return today.isBefore(departureDate) || (today.getYear() == departureDate.getYear()
                && today.getMonth() == departureDate.getMonth() && today.getDay() == departureDate.getDay());
          }
          return false;
        }).collect(Collectors.toList());
        // Clear and add to the observable list
        upcomingDepartures.clear();
        upcomingDepartures.addAll(futureTickets);
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
}