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
import model.entities.Schedule;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.MainAdminVM;
import viewmodel.ModifyTrainVM;

public class MainAdminViewController
{
  private MainAdminVM viewModel;

  @FXML private ListView<Object> trainsListView; // Changed from Train to Object to handle both Train and Schedule items
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
    trainsListView.setItems(viewModel.getDisplayItems());
    trainsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    trainsListView.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<Object>()
    {
      @Override public String toString(Object item)
      {
        if (item == null)
          return "";

        if (item instanceof Train)
        {
          Train train = (Train) item;
          return "Train ID: " + train.getTrainId();
        }

        return item.toString();
      }

      @Override public Object fromString(String string)
      {
        // Not needed for non-editable cells
        return null;
      }
    }));

    // Add listener to handle selection changes
    trainsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      boolean isTrainSelected = newValue instanceof Train;
      viewModel.trainSelectedProperty().set(isTrainSelected);

      if (isTrainSelected)
      {
        // If a train is selected, store it in the viewModel
        viewModel.setSelectedTrain((Train) newValue);
      }
    });

    // Add listener to handle list changes
    viewModel.getDisplayItems().addListener((ListChangeListener<Object>) change -> {
      while (change.next())
      {
        if (change.wasAdded() || change.wasRemoved())
        {
          // If list is empty, disable buttons
          if (viewModel.getDisplayItems().isEmpty())
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
    trainsListView.setItems(viewModel.getDisplayItems());
    removeButton.disableProperty().bind(viewModel.enableRemoveButtonProperty());
    modifyButton.disableProperty().bind(viewModel.enableModifyButtonProperty());

    trainsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      boolean isTrainSelected = newValue instanceof Train;
      viewModel.trainSelectedProperty().set(isTrainSelected);

      if (isTrainSelected)
      {
        viewModel.setSelectedTrain((Train) newValue);
      }
    });
  }

  private void loadTrains()
  {
    viewModel.updateTrainsList();
    try
    {
      viewModel.updateSchedulesList();
      viewModel.createDisplayList();
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
          viewModel.createDisplayList();
        });
      }
      catch (InterruptedException e)
      {
        Thread.currentThread().interrupt();
      }
    }).start();
  }

  @FXML public void onTrainsButton(ActionEvent e)
  {
    // nothing happens, already on this view
    viewModel.updateTrainsList();
    viewModel.createDisplayList();
  }

  @FXML public void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
      ViewHandler.showView(ViewHandler.ViewType.ADMIN_ACCOUNT);
  }

  @FXML public void onAddButton(ActionEvent e)
  {
    if (e.getSource() == addButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.ADD_TRAIN);
    }
  }

  @FXML public void onRemoveButton(ActionEvent e)
  {
    if (e.getSource() == removeButton)
    {
      // Get the selected train from the viewModel, not directly from the listView
      Train selectedTrain = viewModel.getSelectedTrain();
      if (selectedTrain != null && viewModel.confirmDeleteDialog(selectedTrain))
      {
        boolean deleted = viewModel.removeTrain(selectedTrain);
        if (deleted)
        {
          viewModel.showSuccessAlert("Train Deleted", "The train was successfully removed from the system.");
          viewModel.updateTrainsList();
          viewModel.createDisplayList();
        }
      }
    }
  }

  @FXML public void onModifyButton(ActionEvent e)
  {
    if (e.getSource() == modifyButton)
    {
      // Get the selected train from the viewModel, not directly from the listView
      Train selectedTrain = viewModel.getSelectedTrain();
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