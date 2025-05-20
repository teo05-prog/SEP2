package view.admin.modify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Schedule;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.ModifyTrainVM;

public class ModifyViewController
{
  private ModifyTrainVM viewModel;

  @FXML private Label trainIDLabel;

  @FXML private TableView<Schedule> trainTable;
  @FXML private TableColumn<Schedule, String> fromColumn;
  @FXML private TableColumn<Schedule, String> toColumn;
  @FXML private TableColumn<Schedule, String> arrivalColumn;
  @FXML private TableColumn<Schedule, String> departureColumn;
  @FXML private TableColumn<Schedule, Integer> seatsColumn;

  @FXML private Button backButton;
  @FXML private Button saveButton;

  public void init(ModifyTrainVM viewModel)
  {
    this.viewModel = viewModel;
    setupTable();
    bindData();
  }

  private void setupTable()
  {
    fromColumn.setCellValueFactory(cellData -> viewModel.getDepartureStationNameProperty(cellData.getValue()));
    toColumn.setCellValueFactory(cellData -> viewModel.getArrivalStationNameProperty(cellData.getValue()));
    departureColumn.setCellValueFactory(cellData -> viewModel.getDepartureTimeProperty(cellData.getValue()));
    arrivalColumn.setCellValueFactory(cellData -> viewModel.getArrivalTimeProperty(cellData.getValue()));

    // For available seats, we need to adjust since this may be a property of Train rather than Schedule
    // Assuming Schedule has availableSeats property, otherwise this needs to be calculated
    seatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));

    // Handle row selection
    trainTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null)
      {
        viewModel.currentScheduleProperty().set(newSelection);
      }
    });
  }

  private void bindData()
  {
    trainIDLabel.textProperty().bind(viewModel.getTrainIDProperty());
    trainTable.setItems(viewModel.getAvailableSchedules());
    saveButton.disableProperty().bind(viewModel.getSaveButtonDisabledProperty());
  }

  @FXML private void onBack(ActionEvent e)
  {
    if (e.getSource() == backButton)
    {
      // Clean up data from the store
      ViewHandler.clearData("modifyTrainVM");
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
    }
  }

  @FXML private void onSave(ActionEvent e)
  {
    if (e.getSource() == saveButton)
    {
      boolean success = viewModel.saveChanges();
      if (success)
      {
        // Clean up data from the store
        ViewHandler.clearData("modifyTrainVM");
        ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
      }
    }
  }
}