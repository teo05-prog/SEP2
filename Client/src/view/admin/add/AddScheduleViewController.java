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
import viewmodel.AddScheduleVM;

import java.sql.SQLException;

public class AddScheduleViewController
{
  private AddScheduleVM viewModel;

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

  public AddScheduleViewController()
  {
    this.viewModel = new AddScheduleVM();
  }

  public void init(AddScheduleVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
      bindProperties();
    }
  }

  public void initialize() throws SQLException
  {
    if (viewModel == null)
    {
      viewModel = new AddScheduleVM();
    }
    setupUI();
    bindProperties();
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    trainID.textProperty().bind(viewModel.trainIDProperty());
    addTrainButton.disableProperty().bind(viewModel.getAddTrainButtonDisabledProperty());

    viewModel.departureStationProperty().bind(departureStation.valueProperty());
    viewModel.arrivalStationProperty().bind(arrivalStation.valueProperty());
    viewModel.departureTimeProperty().bind(departureTime.valueProperty());
    viewModel.arrivalTimeProperty().bind(arrivalTime.valueProperty());
  }

  private void setupUI() throws SQLException
  {
    ObservableList<String> stations = FXCollections.observableArrayList("Copenhagen", "Aarhus", "Odense", "Aalborg",
        "Esbjerg", "Randers", "Kolding", "Horsens", "Vejle", "Silkeborg", "Herning");
    departureStation.setItems(stations);
    arrivalStation.setItems(stations);

    ObservableList<String> times = FXCollections.observableArrayList();
    for (int hour = 6; hour <= 22; hour++)
    {
      times.add(String.format("%02d:00", hour));
      times.add(String.format("%02d:30", hour));
    }

    departureTime.setItems(times);
    arrivalTime.setItems(times);

    viewModel.arrivalDateProperty().bindBidirectional(arrivalDate.valueProperty());
    viewModel.departureDateProperty().bindBidirectional(departureDate.valueProperty());

    viewModel.generateTrainID();
  }

  public void onAddTrainButton(ActionEvent e)
  {
    if (e.getSource() == addTrainButton)
    {
      viewModel.addTrain();
      if (viewModel.isAddTrainSuccess())
      {
        ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
      }
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