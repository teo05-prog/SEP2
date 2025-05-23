package view.traveller.trains;

import com.google.gson.Gson;
import dtos.TrainDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entities.Schedule;
import model.entities.Station;
import model.entities.Ticket;
import model.entities.Train;
import session.Session;
import view.ViewHandler;
import viewmodel.ChooseTrainVM;
import viewmodel.SearchTicketVM;

import javafx.event.ActionEvent;

import java.util.Random;

public class ChooseTrainViewController
{
  private ChooseTrainVM viewModel;
  private SearchTicketVM searchTicketVM;

  @FXML private Label messageLabel;
  @FXML private ListView<TrainDTO> trainListView;
  @FXML private Button continueButton;
  @FXML private Button backButton;

  public ChooseTrainViewController()
  {
  }

  public void init(ChooseTrainVM viewModel, SearchTicketVM searchTicketVM)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
    }
    this.searchTicketVM = searchTicketVM;
    bindProperties();
    this.viewModel.loadFilteredTrains();
  }

  public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new ChooseTrainVM();
    }
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    trainListView.setItems(viewModel.getTrainList());
    viewModel.selectedTrainProperty().bind(trainListView.getSelectionModel().selectedItemProperty());

    continueButton.disableProperty().bind(viewModel.selectedTrainProperty().isNull());
    continueButton.setOnAction(e -> onContinueButton());
  }

  public void onContinueButton()
  {
    viewModel.continueWithSelectedTrain();

    TrainDTO selectedTrain = viewModel.selectedTrainProperty().get();
    if (selectedTrain == null)
      return;

    if (searchTicketVM != null && searchTicketVM.isSeatOrBicycleSelected())
    {
      ViewHandler.showView(ViewHandler.ViewType.SEAT_SELECTION);
    }
    else
    {
      String email = Session.getInstance().getUserEmail();
      int ticketID = new Random().nextInt(1000000);

      Schedule schedule = new Schedule(selectedTrain.scheduleId, new Station(selectedTrain.from),
          new Station(selectedTrain.to), selectedTrain.departureDate, selectedTrain.arrivalDate);

      Ticket ticket = new Ticket(ticketID, new Train(selectedTrain.trainId), schedule, email);

      System.out.println("Direct ticket creation: " + new Gson().toJson(ticket));
      Session.getInstance().setCurrentTicket(ticket);
      ViewHandler.showView(ViewHandler.ViewType.CONFIRM_TICKET);
    }
  }

  @FXML public void onBackButton(ActionEvent actionEvent)
  {
    if (actionEvent.getSource() == backButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
    }
  }
}
