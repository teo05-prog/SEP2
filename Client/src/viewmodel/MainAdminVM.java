package viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dtos.AddTrainDTO;
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
import java.util.Map;
import java.util.Optional;

public class MainAdminVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableRemoveButton = new SimpleBooleanProperty(true);
  private final BooleanProperty disableModifyButton = new SimpleBooleanProperty(true);
  private final BooleanProperty trainSelected = new SimpleBooleanProperty(false);

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

  public void createDisplayList()
  {
    System.out.println("Creating display list with " + observableTrains.size() + " trains");
    displayItems.clear();
    for (Train train : observableTrains)
    {
      displayItems.add(train);
    }

    System.out.println("Display list now has " + displayItems.size() + " items");
  }

  private Object request(String handler, String action, Object payload) throws Exception
  {
    try (Socket socket = new Socket(HOST, PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
    {
      Request request = new Request(handler, action, payload);
      String jsonRequest = gson.toJson(request);
      out.println(jsonRequest);
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
      int trainId = selectedItem.getTrainId();
      System.out.println("Attempting to delete train with ID: " + trainId);

      Object response = request("trains", "deleteTrain", trainId);
      System.out.println("Server response: " + response);

      if (response != null)
      {
        message.set("Train " + trainId + " removed successfully.");

        trainSelected.set(false);
        selectedTrain = null;

        observableTrains.removeIf(train -> train.getTrainId() == trainId);
        displayItems.removeIf(item -> item instanceof Train && ((Train) item).getTrainId() == trainId);

        updateTrainsList();
        updateSchedulesList();
        createDisplayList();

        System.out.println("Train deletion completed successfully");
        return true;
      }
      else
      {
        message.set("Failed to remove train - no response from server");
        return false;
      }
    }
    catch (Exception e)
    {
      message.set("Error removing train: " + e.getMessage());
      System.err.println("Error during train deletion: " + e.getMessage());
      e.printStackTrace();
      return false;
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

        if (response instanceof TrainList)
        {
          List<Train> newTrains = ((TrainList) response).getTrains();
          System.out.println("Received " + newTrains.size() + " trains from server");
          observableTrains.addAll(newTrains);
        }
        else
        {
          TrainList trainList = gson.fromJson(gson.toJson(response), TrainList.class);
          if (trainList != null)
          {
            List<Train> newTrains = trainList.getTrains();
            System.out.println("Parsed " + newTrains.size() + " trains from JSON");
            observableTrains.addAll(newTrains);
          }
        }

        System.out.println("Observable trains list now has " + observableTrains.size() + " items");
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
        String jsonResponse = gson.toJson(response);
        Type scheduleListType = new TypeToken<List<Schedule>>()
        {
        }.getType();
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

  public boolean addNewTrain()
  {
    try
    {
      Object response = request("addTrain", "getNextTrainId", null);
      if (response == null)
      {
        message.set("Error getting next train ID from server");
        return false;
      }
      String jsonResponse = gson.toJson(response);
      Map<String, Object> responseMap = gson.fromJson(jsonResponse, Map.class);
      Double nextTrainIdDouble = (Double) responseMap.get("nextTrainId");
      if (nextTrainIdDouble == null)
      {
        message.set("Server returned invalid train ID");
        return false;
      }
      int nextTrainId = nextTrainIdDouble.intValue();
      AddTrainDTO addRequest = new AddTrainDTO(nextTrainId);
      Object createResponse = request("addTrain", "train", addRequest);
      if (createResponse != null)
      {
        message.set("Train " + nextTrainId + " added successfully");
        updateTrainsList();
        createDisplayList();
        return true;
      }
      else
      {
        message.set("Error creating train");
        return false;
      }
    }
    catch (Exception e)
    {
      message.set("Error creating train: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}