package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Ticket;

public class UpcomingDeparturesVM
{
  private ObservableList<Ticket> upcomingDepartures;

  public UpcomingDeparturesVM()
  {
    upcomingDepartures = FXCollections.observableArrayList();
    loadUpcomingDepartures();
  }

  private void loadUpcomingDepartures()
  {
    // empty list for now
  }

  public ObservableList<Ticket> getUpcomingDepartures()
  {
    return upcomingDepartures;
  }

  // refresh data when needed
  public void refreshData() {
    loadUpcomingDepartures();
  }
}
