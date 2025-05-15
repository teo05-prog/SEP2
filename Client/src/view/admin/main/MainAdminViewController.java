package view.admin.main;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.entities.Schedule;
import model.entities.Train;
import view.ViewHandler;
import viewmodel.MainAdminVM;

public class MainAdminViewController
{
  private MainAdminVM viewModel;

  @FXML private ListView<Train> trainsListView;
  @FXML private ListView<Schedule> schedulesListView;

  @FXML private Label messageLabel;

  @FXML private Button trainsButton;
  @FXML private Button schedulesButton;
  @FXML private Button myAccountButton;
  @FXML private Button addButton;
  @FXML private Button removeButton;
  @FXML private Button modifyButton;

  public MainAdminViewController()
  {
    try {
      this.viewModel = new MainAdminVM();
    } catch (Exception e) {
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
      try {
        viewModel = new MainAdminVM();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    bindProperties();
    trainsButton.setDisable(true);
    setupTrainsListView();
    setupSchedulesListView();
    viewModel.updateTrainsList();
    try {
      viewModel.updateSchedulesList();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setupTrainsListView()
  {
    trainsListView.setItems(viewModel.getTrains());
    trainsListView.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<Train>()
    {
      @Override public String toString(Train train)
      {
        if (train == null)
          return "";
        try
        {
          return train.toString();
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

  private void setupSchedulesListView()
  {
    if (schedulesListView != null) {
      schedulesListView.setItems(viewModel.getSchedules());
      schedulesListView.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<Schedule>()
      {
        @Override public String toString(Schedule schedule)
        {
          if (schedule == null)
            return "";
          try
          {
            return schedule.toString();
          }
          catch (NullPointerException e)
          {
            // Handle case where schedule fields are null
            return "Schedule missing details";
          }
        }

        @Override public Schedule fromString(String string)
        {
          // Not needed for non-editable cells
          return null;
        }
      }));

      // Add listener to handle selection changes
      schedulesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        viewModel.trainSelectedProperty().set(newValue != null);
      });
    }
  }

  private void bindProperties()
  {
    messageLabel.textProperty().bind(viewModel.messageProperty());
    trainsListView.setItems(viewModel.getTrains());
    if (schedulesListView != null) {
      schedulesListView.setItems(viewModel.getSchedules());
    }
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

  public void onTrainsButton(ActionEvent e)
  {
    // nothing happens, already on this view
    viewModel.updateTrainsList();
  }

  public void onSchedulesButton(ActionEvent e)
  {
    if (e.getSource() == schedulesButton) {
      try {
        viewModel.updateSchedulesList();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
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
      viewModel.removeTrain(trainsListView.getSelectionModel().getSelectedItem());
    }
  }

  public void onModifyButton(ActionEvent e)
  {
    if (e.getSource() == modifyButton)
      ViewHandler.showView(ViewHandler.ViewType.MODIFY_TRAIN);
  }
}