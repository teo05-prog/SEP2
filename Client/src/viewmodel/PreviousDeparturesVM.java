package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Ticket;

public class PreviousDeparturesVM
{
  private ObservableList<Ticket> previousDepartures;

  public PreviousDeparturesVM()
  {
    previousDepartures = FXCollections.observableArrayList();
    loadPreviousDepartures();
  }

  private void loadPreviousDepartures()
  {
    // empty list for now

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
