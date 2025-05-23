package view.admin.add;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.ViewHandler;
import viewmodel.AddScheduleVM;

import java.sql.SQLException;

public class AddScheduleViewController
{
  private AddScheduleVM viewModel;

  @FXML private Label messageLabel;
  @FXML private ComboBox<String> departureStation;
  @FXML private DatePicker departureDate;
  @FXML private ComboBox<String> departureTime;

  @FXML private ComboBox<String> arrivalStation;
  @FXML private DatePicker arrivalDate;
  @FXML private ComboBox<String> arrivalTime;

  @FXML private Button addButton;
  @FXML private Button backButton;

  public AddScheduleViewController()
  {
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
    AddScheduleVM passedVM = (AddScheduleVM) ViewHandler.getData("addScheduleVM");
    if (passedVM != null)
    {
      this.viewModel = passedVM;
    }
    else if (viewModel == null)
    {
      viewModel = new AddScheduleVM();
    }
    setupUI();
    bindProperties();
  }

  private void bindProperties()
  {
    addButton.disableProperty().bind(viewModel.getAddButtonDisabledProperty());
    messageLabel.textProperty().bind(viewModel.messageProperty());
    viewModel.departureStationProperty().bind(departureStation.valueProperty());
    viewModel.arrivalStationProperty().bind(arrivalStation.valueProperty());
    viewModel.departureTimeProperty().bind(departureTime.valueProperty());
    viewModel.arrivalTimeProperty().bind(arrivalTime.valueProperty());
  }

  private void setupUI()
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

    if (departureDate.getValue() == null)
    {
      departureDate.setValue(java.time.LocalDate.now());
    }

    if (arrivalDate.getValue() == null)
    {
      arrivalDate.setValue(java.time.LocalDate.now());
    }

    viewModel.departureDateProperty().set(departureDate.getValue());
    viewModel.arrivalDateProperty().set(arrivalDate.getValue());

    viewModel.arrivalDateProperty().bindBidirectional(arrivalDate.valueProperty());
    viewModel.departureDateProperty().bindBidirectional(departureDate.valueProperty());
  }

  public void onAddButton(ActionEvent e)
  {
    if (e.getSource() == addButton)
    {
      boolean allFieldsFilled = departureStation.getValue() != null && !departureStation.getValue().isEmpty()
          && arrivalStation.getValue() != null && !arrivalStation.getValue().isEmpty()
          && departureTime.getValue() != null && !departureTime.getValue().isEmpty() && arrivalTime.getValue() != null
          && !arrivalTime.getValue().isEmpty() && departureDate.getValue() != null && arrivalDate.getValue() != null;

      if (!allFieldsFilled)
      {
        messageLabel.textProperty().unbind();
        messageLabel.setText("Please fill in all fields before adding a schedule.");
        return;
      }

      boolean success = viewModel.addSchedule();

      if (success)
      {
        messageLabel.textProperty().unbind();
        messageLabel.setText("Schedule added successfully!");
        new Thread(() -> {
          try
          {
            Thread.sleep(3000);
            javafx.application.Platform.runLater(() -> {
              messageLabel.setText("");
              ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
            });
          }
          catch (InterruptedException ex)
          {
            Thread.currentThread().interrupt();
          }
        }).start();
      }
    }
  }

  public void onBackButton(ActionEvent e)
  {
    if (e.getSource() == backButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.MODIFY_TRAIN);
    }
  }
}