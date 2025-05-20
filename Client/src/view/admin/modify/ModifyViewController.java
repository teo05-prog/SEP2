package view.admin.modify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.entities.Schedule;
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
  }

  private void bindData()
  {
    trainIDLabel.textProperty().bind(viewModel.getTrainIDProperty());
    trainTable.setItems(viewModel.getTrainSchedules());
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