package viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.deserializer.ScheduleDeserializer;
import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.entities.Schedule;
import model.entities.Train;
import model.entities.TrainList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class MainAdminVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableRemoveButton = new SimpleBooleanProperty(true);
  private final BooleanProperty disableModifyButton = new SimpleBooleanProperty(true);
  private final BooleanProperty trainSelected = new SimpleBooleanProperty(false);
  private final BooleanProperty scheduleSelected = new SimpleBooleanProperty(false);

  private final ObservableList<Train> observableTrains = FXCollections.observableArrayList();
  private final ObservableList<Schedule> observableSchedules = FXCollections.observableArrayList();
  private final ObservableList<Object> displayItems = FXCollections.observableArrayList();

  private Train selectedTrain;

  private final Gson gson = new GsonBuilder().registerTypeAdapter(Schedule.class, new ScheduleDeserializer()).create();
  private static final String HOST = "localhost";
  private static final int PORT = 4892;

  public MainAdminVM()
  {
    disableRemoveButton.bind(trainSelected.not());
    disableModifyButton.bind(trainSelected.not());
    updateTrainsList();
    try
    {
      updateSchedulesList();
      createDisplayList();
    }
    catch (Exception e)
    {
      message.set("Error loading schedules: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public BooleanProperty trainSelectedProperty()
  {
    return trainSelected;
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public BooleanProperty enableRemoveButtonProperty()
  {
    return disableRemoveButton;
  }

  public BooleanProperty enableModifyButtonProperty()
  {
    return disableModifyButton;
  }

  public ObservableList<Train> getTrains()
  {
    return observableTrains;
  }

  public ObservableList<Schedule> getSchedules()
  {
    return observableSchedules;
  }

  public ObservableList<Object> getDisplayItems()
  {
    return displayItems;
  }

  public void setSelectedTrain(Train train)
  {
    this.selectedTrain = train;
  }

  public Train getSelectedTrain()
  {
    return selectedTrain;
  }

  public String formatTrainDisplay(Train train)
  {
    if (train.getSchedule() != null && train.getSchedule().getDepartureStation() != null
        && train.getSchedule().getArrivalStation() != null)
    {

      return String.format("Train ID: %d, From: %s, To: %s", train.getTrainId(),
          train.getSchedule().getDepartureStation().getName(), train.getSchedule().getArrivalStation().getName());
    }
    else
    {
      return String.format("Train ID: %d, No schedule", train.getTrainId());
    }
  }

  public void createDisplayList()
  {
    displayItems.clear();
    for (Train train : observableTrains)
    {
      displayItems.add(train);
    }
  }

  private Object request(String handler, String action, Object payload) throws Exception
  {
    try (Socket socket = new Socket(HOST, PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
    {
      // Create and send request
      Request request = new Request(handler, action, payload);
      String jsonRequest = gson.toJson(request);
      out.println(jsonRequest);
      // Read and parse response
      String jsonResponse = in.readLine();
      Response response = gson.fromJson(jsonResponse, Response.class);

      if (response.status().equals("SUCCESS"))
      {
        return response.payload();
      }
      else if (response.status().equals("ERROR"))
      {
        ErrorResponse error = gson.fromJson(gson.toJson(response.payload()), ErrorResponse.class);
        throw new Exception(error.errorMessage());
      }
      else
      {
        ErrorResponse error = gson.fromJson(gson.toJson(response.payload()), ErrorResponse.class);
        throw new Exception("Server failure: " + error.errorMessage());
      }
    }
    catch (IOException e)
    {
      throw new Exception("Network error: " + e.getMessage());
    }
  }

  public boolean removeTrain(Train selectedItem)
  {
    if (selectedItem == null)
    {
      message.set("No train selected.");
      return false;
    }
    try
    {
      request("trains", "deleteTrain", selectedItem.getTrainId());
      message.set("Train " + selectedItem.getTrainId() + " removed.");
      trainSelected.set(false);
      selectedTrain = null;
      updateTrainsList();
      updateSchedulesList();
      createDisplayList();
      return true;
    }
    catch (Exception e)
    {
      message.set("Error removing train: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public void removeSchedule(Schedule selectedItem)
  {
    if (selectedItem == null)
    {
      message.set("No schedule selected.");
      return;
    }
    try
    {
      request("schedules", "deleteSchedule", selectedItem);
      message.set(
          "Schedule from " + selectedItem.getDepartureStation().getName() + " to " + selectedItem.getArrivalStation()
              .getName() + " removed.");
      scheduleSelected.set(false);
      updateSchedulesList();
      createDisplayList();
    }
    catch (Exception e)
    {
      message.set("Error removing schedule: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void updateTrainsList()
  {
    try
    {
      Object response = request("trains", "getAllTrains", null);
      if (response != null)
      {
        observableTrains.clear();

        // Handle different response types correctly
        if (response instanceof TrainList)
        {
          observableTrains.addAll(((TrainList) response).getTrains());
        }
        else
        {
          TrainList trainList = gson.fromJson(gson.toJson(response), TrainList.class);
          if (trainList != null)
          {
            observableTrains.addAll(trainList.getTrains());
          }
        }
      }
    }
    catch (Exception e)
    {
      message.set("Error loading trains: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void updateSchedulesList()
  {
    try
    {
      Object response = request("schedules", "getAllSchedules", null);

      if (response != null)
      {
        observableSchedules.clear();

        // Convert the response to JSON string
        String jsonResponse = gson.toJson(response);

        // Define the proper type token for Schedule list
        Type scheduleListType = new TypeToken<List<Schedule>>()
        {
        }.getType();

        // Deserialize properly with type information
        List<Schedule> schedules = gson.fromJson(jsonResponse, scheduleListType);

        if (schedules != null)
        {
          observableSchedules.addAll(schedules);
        }
      }
    }
    catch (Exception e)
    {
      message.set("Error loading schedules: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void modifyTrain(Train selectedItem)
  {
    if (selectedItem == null)
    {
      message.set("No train selected.");
      return;
    }
    try
    {
      message.set("Preparing to modify train " + selectedItem.getTrainId() + ".");
    }
    catch (Exception e)
    {
      message.set("Error preparing train modification: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void modifySchedule(Schedule selectedItem)
  {
    if (selectedItem == null)
    {
      message.set("No schedule selected.");
      return;
    }
    try
    {
      // Handle schedule modification logic here
      message.set("Preparing to modify schedule from " + selectedItem.getDepartureStation().getName() + " to "
          + selectedItem.getArrivalStation().getName() + ".");
    }
    catch (Exception e)
    {
      message.set("Error preparing schedule modification: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean confirmDeleteDialog(Train train)
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm Deletion");
    alert.setHeaderText("Delete Train");
    alert.setContentText("Are you sure you want to delete train " + train.getTrainId() + "?");

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
  }

  public void showSuccessAlert(String title, String message)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}