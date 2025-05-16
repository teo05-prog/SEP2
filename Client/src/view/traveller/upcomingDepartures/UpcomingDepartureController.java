package view.traveller.upcomingDepartures;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.entities.Ticket;
import view.ViewHandler;
import viewmodel.UpcomingDeparturesVM;

public class UpcomingDepartureController
{
  private UpcomingDeparturesVM viewModel;

  @FXML private Button startButton;
  @FXML private Button previousButton;
  @FXML private Button upcomingButton;
  @FXML private Button myAccountButton;
  @FXML private ListView<Ticket> upcomingDeparturesListView;

  public UpcomingDepartureController()
  {
    this.viewModel = new UpcomingDeparturesVM();
  }

  public void init(UpcomingDeparturesVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
    }
    setupUI();
    bindProperties();
  }

  @FXML public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new UpcomingDeparturesVM();
    }
  }

  private void setupUI()
  {
    upcomingButton.setDisable(true);
  }

  public void bindProperties()
  {
    // Bind the ListView to the upcoming departures
    upcomingDeparturesListView.setItems(viewModel.getUpcomingDepartures());

    // customize how tickets are displayed
    //can be deleted if you want the departures to be displayed as default))
    upcomingDeparturesListView.setCellFactory(param -> new ListCell<Ticket>()
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
          setText(ticket.getTrainId() + " - " + "Departure: " + ticket.getScheduleId().getDepartureDate().toString() + " Arrival: "
              + ticket.getScheduleId().getArrivalDate().toString());
        }
      }
    });
  }

  public void onStartButton(ActionEvent e)
  {
    if (e.getSource() == startButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
    }
  }

  public void onPreviousButton(ActionEvent e)
  {
    if (e.getSource() == previousButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.PREVIOUS);
    }
  }

  public void onUpcomingButton()
  {
    // do nothing because we are already on Upcoming departures page
  }

  public void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.USER_ACCOUNT);
    }
  }
}
