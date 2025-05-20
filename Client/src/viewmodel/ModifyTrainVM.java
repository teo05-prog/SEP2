package viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import model.entities.Schedule;
import model.entities.Train;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModifyTrainVM
{
  private final StringProperty trainID = new SimpleStringProperty();
  private final BooleanProperty saveButtonDisabled = new SimpleBooleanProperty(true);
  private final ObjectProperty<Schedule> currentSchedule = new SimpleObjectProperty<>();

  private final ObservableList<Schedule> availableSchedules = FXCollections.observableArrayList();

  private Train selectedTrain;
  private Schedule originalSchedule;

  private final Gson gson = new GsonBuilder().create();
  private static final String HOST = "localhost";
  private static final int PORT = 4892;
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public ModifyTrainVM()
  {
    currentSchedule.addListener((obs, oldVal, newVal) -> {
      if (selectedTrain != null)
      {
        boolean scheduleChanged =
            (originalSchedule == null && newVal != null) || (originalSchedule != null && !originalSchedule.equals(
                newVal));
        saveButtonDisabled.set(!scheduleChanged);
      }
    });
  }

  public StringProperty getTrainIDProperty()
  {
    return trainID;
  }

  public BooleanProperty getSaveButtonDisabledProperty()
  {
    return saveButtonDisabled;
  }

  public ObjectProperty<Schedule> currentScheduleProperty()
  {
    return currentSchedule;
  }

  public ObservableList<Schedule> getAvailableSchedules()
  {
    return availableSchedules;
  }

  public void loadTrainData(Train train)
  {
    this.selectedTrain = train;
    trainID.set("Train ID: " + train.getTrainId());
    originalSchedule = train.getSchedule();

    if (originalSchedule != null)
    {
      currentSchedule.set(originalSchedule);
    }
    else
    {
      currentSchedule.set(null);
    }

    loadAllSchedules();
  }

  private void loadAllSchedules()
  {
    try
    {
      Object response = request("schedules", "getAllSchedules", null);
      if (response != null)
      {
        availableSchedules.clear();

        // Convert the response to a list of schedules
        java.util.List<Schedule> schedules = gson.fromJson(gson.toJson(response),
            new com.google.gson.reflect.TypeToken<java.util.List<Schedule>>()
            {
            }.getType());

        if (schedules != null)
        {
          availableSchedules.addAll(schedules);
        }
        System.out.println("Loaded " + availableSchedules.size() + " schedules.");
      }
    }
    catch (Exception e)
    {
      System.out.println("Error loading schedules: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean saveChanges()
  {
    if (selectedTrain == null)
    {
      System.out.println("No train selected!");
      return false;
    }
    try
    {
      // Update the train with the new schedule
      selectedTrain.setSchedule(currentSchedule.get());

      // Send update to the server
      request("trains", "updateTrain", selectedTrain);
      System.out.println("Train " + selectedTrain.getTrainId() + " schedule updated successfully.");
      return true;
    }
    catch (Exception e)
    {
      System.out.println("Error saving changes: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public StringProperty getDepartureStationNameProperty(Schedule schedule)
  {
    StringProperty property = new SimpleStringProperty();
    if (schedule != null && schedule.getDepartureStation() != null)
    {
      property.set(schedule.getDepartureStation().getName());
    }
    else
    {
      property.set("N/A");
    }
    return property;
  }

  public StringProperty getArrivalStationNameProperty(Schedule schedule)
  {
    StringProperty property = new SimpleStringProperty();
    if (schedule != null && schedule.getArrivalStation() != null)
    {
      property.set(schedule.getArrivalStation().getName());
    }
    else
    {
      property.set("N/A");
    }
    return property;
  }

  public StringProperty getDepartureTimeProperty(Schedule schedule)
  {
    StringProperty property = new SimpleStringProperty();
    if (schedule != null && schedule.getDepartureDate() != null)
    {
      LocalDateTime departureTime = schedule.getDepartureDate().toLocalDateTime();
      property.set(formatDateTime(departureTime));
    }
    else
    {
      property.set("N/A");
    }
    return property;
  }

  public StringProperty getArrivalTimeProperty(Schedule schedule)
  {
    StringProperty property = new SimpleStringProperty();
    if (schedule != null && schedule.getArrivalDate() != null)
    {
      LocalDateTime arrivalTime = schedule.getArrivalDate().toLocalDateTime();
      property.set(formatDateTime(arrivalTime));
    }
    else
    {
      property.set("N/A");
    }
    return property;
  }

  private String formatDateTime(LocalDateTime dateTime)
  {
    if (dateTime == null)
      return "";
    return dateTime.format(DATE_FORMATTER) + " " + dateTime.format(TIME_FORMATTER);
  }

  public StringConverter<Schedule> getScheduleStringConverter()
  {
    return new StringConverter<Schedule>()
    {
      @Override public String toString(Schedule schedule)
      {
        if (schedule == null)
        {
          return "No Schedule";
        }
        return schedule.getDepartureStation().getName() + " → " + schedule.getArrivalStation().getName() + " ("
            + formatDateTime(schedule.getDepartureDate().toLocalDateTime()) + ")";
      }

      @Override public Schedule fromString(String string)
      {
        // Not needed for non-editable combo box
        return null;
      }
    };
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
}
