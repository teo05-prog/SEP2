package view.admin.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.MainAdminVM;

public class MainAdminViewController
{
  private MainAdminVM viewModel;

  @FXML private ListView<Train> trainsListView;

  @FXML private Label messageLabel;

  @FXML private Button trainsButton;
  @FXML private Button myAccountButton;
  @FXML private Button addButton;
  @FXML private Button removeButton;
  @FXML private Button modifyButton;

  public MainAdminViewController()
  {
    this.viewModel = new MainAdminVM();
  }

  public void init(MainAdminVM viewModel)
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
      viewModel = new MainAdminVM();
    }
    bindProperties();
    trainsButton.setDisable(true);
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    // trainsListView.setItems(viewModel.getTrains());
    removeButton.disableProperty().bind(viewModel.enableRemoveButtonProperty());
    modifyButton.disableProperty().bind(viewModel.enableModifyButtonProperty());
    trainsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      viewModel.trainSelectedProperty().set(newValue != null);
    });
  }

  public void showAddTrainSuccess()
  {
    messageLabel.textProperty().unbind();
    messageLabel.setText("Train added successfully!");

    new Thread(() -> {
      try
      {
        Thread.sleep(3000);
        javafx.application.Platform.runLater(() -> {
          messageLabel.setText("");
          messageLabel.textProperty().bind(viewModel.messageProperty()); // Rebind
        });
      }
      catch (InterruptedException e)
      {
        Thread.currentThread().interrupt();
      }
    }).start();
  }

  public void onTrainsButton(ActionEvent e)
  {
    // nothing happens, already on this view
  }

  public void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
      ViewHandler.showView(ViewHandler.ViewType.ADMIN_ACCOUNT);
  }

  public void onAddButton(ActionEvent e)
  {
    if (e.getSource() == addButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.ADD_TRAIN);
    }
  }

  public void onRemoveButton(ActionEvent e)
  {
    if (e.getSource() == removeButton)
    {
      viewModel.removeTrain(trainsListView.getSelectionModel().getSelectedItem()); // to be implemented
      viewModel.updateTrainsList(); // refresh the list
    }
    // remove selected train
  }

  public void onModifyButton(ActionEvent e)
  {
    if (e.getSource() == modifyButton)
      ViewHandler.showView(ViewHandler.ViewType.MODIFY_TRAIN);
  }
}
