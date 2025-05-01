package view.admin.add;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import view.ViewHandler;
import viewmodel.AddTrainVM;

public class AddTrainViewController
{
  private AddTrainVM viewModel;

  @FXML private Label trainID;
  @FXML private Label messageLabel;
  @FXML private ComboBox<String> departureStation;
  @FXML private DatePicker departureDate;
  @FXML private ComboBox<String> departureTime;

  @FXML private ComboBox<String> arrivalStation;
  @FXML private DatePicker arrivalDate;
  @FXML private ComboBox<String> arrivalTime;

  @FXML private Button addTrainButton;
  @FXML private Button backButton;

  public AddTrainViewController()
  {
    this.viewModel = new AddTrainVM();
  }

  public void init(AddTrainVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
      bindProperties();
    }
  }

  public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new AddTrainVM();
    }
    setupUI();
    bindProperties();
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    trainID.textProperty().bind(viewModel.trainIDProperty());
    addTrainButton.disableProperty().bind(viewModel.getAddTrainButtonDisabledProperty());
  }

  private void setupUI()
  {
    ObservableList<String> stations = FXCollections.observableArrayList("Copenhagen", "Aarhus", "Odense", "Aalborg",
        "Esbjerg");
    departureStation.setItems(stations);
    arrivalStation.setItems(stations);
    ObservableList<String> times = FXCollections.observableArrayList();
    for (int hour = 6; hour <= 22; hour++)
    {
      times.add(String.format("%02d:00", hour));
      times.add(String.format("%02d:30", hour));
    }
    viewModel.arrivalDateProperty().bindBidirectional(arrivalDate.valueProperty());
    viewModel.departureDateProperty().bindBidirectional(departureDate.valueProperty());

    departureTime.setItems(times);
    arrivalTime.setItems(times);
  }

  public void onAddTrainButton(ActionEvent e)
  {
    if (e.getSource() == addTrainButton)
    {
      viewModel.addTrain();
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
    }
  }

  public void onBackButton(ActionEvent e)
  {
    if (e.getSource() == backButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
    }
  }
}
