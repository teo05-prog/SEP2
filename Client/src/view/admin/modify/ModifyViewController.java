package view.admin.modify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import model.entities.Schedule;
import view.ViewHandler;
import viewmodel.ModifyTrainVM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ModifyViewController
{
  private ModifyTrainVM viewModel;
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @FXML private Label trainIDLabel;
  @FXML private Label errorMessageLabel;

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
    trainTable.setEditable(true);

    fromColumn.setCellValueFactory(cellData -> viewModel.getDepartureStationNameProperty(cellData.getValue()));
    toColumn.setCellValueFactory(cellData -> viewModel.getArrivalStationNameProperty(cellData.getValue()));
    departureColumn.setCellValueFactory(cellData -> viewModel.getDepartureTimeProperty(cellData.getValue()));
    arrivalColumn.setCellValueFactory(cellData -> viewModel.getArrivalTimeProperty(cellData.getValue()));

    fromColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    fromColumn.setOnEditCommit(event -> {
      Schedule schedule = event.getRowValue();
      boolean success = viewModel.updateDepartureStationName(schedule, event.getNewValue());
      if (!success)
      {
        showErrorAlert(viewModel.getErrorMessageProperty().get());
        trainTable.refresh();
      }
    });

    toColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    toColumn.setOnEditCommit(event -> {
      Schedule schedule = event.getRowValue();
      boolean success = viewModel.updateArrivalStationName(schedule, event.getNewValue());
      if (!success)
      {
        showErrorAlert(viewModel.getErrorMessageProperty().get());
        trainTable.refresh();
      }
    });

    departureColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    departureColumn.setOnEditCommit(event -> {
      Schedule schedule = event.getRowValue();
      try
      {
        LocalDateTime dateTime = LocalDateTime.parse(event.getNewValue(), DATE_TIME_FORMATTER);
        viewModel.updateDepartureTime(schedule, dateTime);
      }
      catch (DateTimeParseException e)
      {
        showErrorAlert("Invalid datetime format. Please use format: yyyy-MM-dd HH:mm");
        trainTable.refresh();
      }
    });

    arrivalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    arrivalColumn.setOnEditCommit(event -> {
      Schedule schedule = event.getRowValue();
      try
      {
        LocalDateTime dateTime = LocalDateTime.parse(event.getNewValue(), DATE_TIME_FORMATTER);
        viewModel.updateArrivalTime(schedule, dateTime);
      }
      catch (DateTimeParseException e)
      {
        showErrorAlert("Invalid datetime format. Please use format: yyyy-MM-dd HH:mm");
        trainTable.refresh();
      }
    });
  }

  private void bindData()
  {
    trainIDLabel.textProperty().bind(viewModel.getTrainIDProperty());
    if (errorMessageLabel != null)
    {
      errorMessageLabel.textProperty().bind(viewModel.getErrorMessageProperty());
    }
    trainTable.setItems(viewModel.getTrainSchedules());
    saveButton.disableProperty().bind(viewModel.getSaveButtonDisabledProperty());
  }

  private void showErrorAlert(String message)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Input Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML private void onBack(ActionEvent e)
  {
    if (e.getSource() == backButton)
    {
      if (!viewModel.getSaveButtonDisabledProperty().get())
      {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("There are unsaved changes");
        alert.setContentText("Do you want to discard your changes?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(button -> {
          if (button == buttonTypeYes)
          {
            ViewHandler.clearData("modifyTrainVM");
            ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
          }
        });
      }
      else
      {
        ViewHandler.clearData("modifyTrainVM");
        ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
      }
    }
  }

  @FXML private void onSave(ActionEvent e)
  {
    if (e.getSource() == saveButton)
    {
      boolean success = viewModel.saveChanges();
      if (success)
      {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Train schedule updated successfully!");
        alert.showAndWait();
        ViewHandler.clearData("modifyTrainVM");
        ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
      }
      else
      {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);

        // Get the specific error message from the view model
        String errorMsg = viewModel.getErrorMessageProperty().get();
        if (errorMsg == null || errorMsg.isEmpty())
        {
          errorMsg = "Failed to update train schedule. Please try again.";
        }

        alert.setContentText(errorMsg);
        alert.showAndWait();
      }
    }
  }
}