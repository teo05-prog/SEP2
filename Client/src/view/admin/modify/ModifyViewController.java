package view.admin.modify;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.ModifyTrainVM;

public class ModifyViewController
{
  private ModifyTrainVM viewModel;

  @FXML private TableView<Train> trainTable;
  @FXML private TableColumn<Train, String> fromColumn;
  @FXML private TableColumn<Train, String> toColumn;
  @FXML private TableColumn<Train, String> arrivalColumn;
  @FXML private TableColumn<Train, String> departureColumn;
  @FXML private TableColumn<Train, Integer> seatsColumn;

  @FXML private Label trainIDLabel;
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
    fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
    toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
    arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
    departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
    seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
  }

  private void bindData()
  {
    trainTable.setItems(viewModel.getTrainData());
    trainIDLabel.textProperty().bind(viewModel.getTrainIDProperty());
    saveButton.disableProperty().bind(viewModel.getSaveButtonDisabledProperty());
  }

  @FXML
  private void onBack(ActionEvent e)
  {
    if(e.getSource() == backButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
    }
  }

  @FXML
  private void onSave(ActionEvent e)
  {
    if(e.getSource() == saveButton)
    {
      viewModel.saveChanges();
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
    }
  }
}