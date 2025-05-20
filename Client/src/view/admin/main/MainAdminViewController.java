package view.admin.main;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.MainAdminVM;
import viewmodel.ModifyTrainVM;

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
    try
    {
      this.viewModel = new MainAdminVM();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
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
      try
      {
        viewModel = new MainAdminVM();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    bindProperties();
    trainsButton.setDisable(true);
    setupTrainsListView();
    loadTrains();
  }

  private void setupTrainsListView()
  {
    trainsListView.setItems(viewModel.getTrains());
    trainsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    trainsListView.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<Train>()
    {
      @Override public String toString(Train train)
      {
        if (train == null)
          return "";
        try
        {
          return viewModel.formatTrainDisplay(train);
        }
        catch (NullPointerException e)
        {
          // Handle case where schedule is null
          return "Train ID: " + train.getTrainId() + ", No schedule";
        }
      }

      @Override public Train fromString(String string)
      {
        // Not needed for non-editable cells
        return null;
      }
    }));

    // Add listener to handle selection changes
    trainsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      viewModel.trainSelectedProperty().set(newValue != null);
      boolean hasSelection = newValue != null;
      removeButton.setDisable(!hasSelection);
      modifyButton.setDisable(!hasSelection);
    });

    // Add listener to handle list changes
    viewModel.getTrains().addListener((ListChangeListener<Train>) change -> {
      while (change.next())
      {
        if (change.wasAdded() || change.wasRemoved())
        {
          // If list is empty, disable buttons
          if (viewModel.getTrains().isEmpty())
          {
            viewModel.trainSelectedProperty().set(false);
          }
        }
      }
    });
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    trainsListView.setItems(viewModel.getTrains());
    removeButton.disableProperty().bind(viewModel.enableRemoveButtonProperty());
    modifyButton.disableProperty().bind(viewModel.enableModifyButtonProperty());

    trainsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      viewModel.trainSelectedProperty().set(newValue != null);
    });
  }

  private void loadTrains()
  {
    viewModel.updateTrainsList();
    try
    {
      viewModel.updateSchedulesList();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
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
          messageLabel.textProperty().bind(viewModel.messageProperty());
          viewModel.updateTrainsList();
        });
      }
      catch (InterruptedException e)
      {
        Thread.currentThread().interrupt();
      }
    }).start();
  }

  @FXML
  public void onTrainsButton(ActionEvent e)
  {
    // nothing happens, already on this view
    viewModel.updateTrainsList();
  }

  @FXML
  public void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
      ViewHandler.showView(ViewHandler.ViewType.ADMIN_ACCOUNT);
  }

  @FXML
  public void onAddButton(ActionEvent e)
  {
    if (e.getSource() == addButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.ADD_TRAIN);
    }
  }

  @FXML
  public void onRemoveButton(ActionEvent e)
  {
    if (e.getSource() == removeButton)
    {
      Train selectedTrain = trainsListView.getSelectionModel().getSelectedItem();
      if (selectedTrain != null && viewModel.confirmDeleteDialog(selectedTrain))
      {
        boolean deleted = viewModel.removeTrain(selectedTrain);
        if (deleted)
        {
          viewModel.showSuccessAlert("Train Deleted", "The train was successfully removed from the system.");
          viewModel.updateTrainsList();
        }
      }
    }
  }

  @FXML
  public void onModifyButton(ActionEvent e)
  {
    if (e.getSource() == modifyButton)
    {
      Train selectedTrain = trainsListView.getSelectionModel().getSelectedItem();
      if (selectedTrain != null)
      {
        // Create and initialize ModifyTrainVM with the selected train
        ModifyTrainVM modifyVM = new ModifyTrainVM();
        modifyVM.loadTrainData(selectedTrain);

        // Store the viewmodel in the ViewHandler's data store
        ViewHandler.setData("modifyTrainVM", modifyVM);
        ViewHandler.showView(ViewHandler.ViewType.MODIFY_TRAIN);
      }
      else
      {
        viewModel.messageProperty().set("No train selected!");
      }
    }
  }
}