package view.traveller.previousDepartures;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.entities.Ticket;
import view.ViewHandler;
import viewmodel.PreviousDeparturesVM;

public class PreviousDeparturesController
{
  private PreviousDeparturesVM viewModel;

  @FXML private Button startButton;
  @FXML private Button previousButton;
  @FXML private Button upcomingButton;
  @FXML private Button myAccountButton;
  @FXML private ListView<Ticket> PreviousDeparturesListView;

  public PreviousDeparturesController()
  {
  }

  public void init(PreviousDeparturesVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
    }
    else
    {
      this.viewModel = new PreviousDeparturesVM();
    }
    setupUI();
    bindProperties();
  }

  @FXML public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new PreviousDeparturesVM();
      viewModel.loadPreviousDepartures();
    }
  }

  public void bindProperties()
  {
    // Bind the ListView to the upcoming departures
    PreviousDeparturesListView.setItems(viewModel.getPreviousDepartures());

    PreviousDeparturesListView.setCellFactory(param -> new ListCell<Ticket>()
    {
      @Override protected void updateItem(Ticket ticket, boolean empty)
      {
        super.updateItem(ticket, empty);

        if (empty || ticket == null)
        {
          setText(null);
        }
        else
        {
          setText(ticket.getTrainId() + " - " + "Departure: " + ticket.getScheduleId().getDepartureDate().toString()
              + " Arrival: " + ticket.getScheduleId().getArrivalDate().toString());
        }
      }
    });
  }

  private void setupUI()
  {
    previousButton.setDisable(true);
    viewModel.loadPreviousDepartures();
  }

  @FXML public void onStartButton(ActionEvent e)
  {
    if (e.getSource() == startButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
    }
  }

  @FXML public void onPreviousButton(ActionEvent e)
  {
    // do nothing because we are already on Previous departures page
  }

  public void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.USER_ACCOUNT);
    }
  }

  @FXML public void onUpcomingButton(ActionEvent e)
  {
    if (e.getSource() == upcomingButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.UPCOMING);
    }
  }

}
