package viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import model.entities.MyDate;
import model.entities.Schedule;
import model.entities.Station;
import model.entities.Train;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModifyTrainVM
{
  private final StringProperty trainID = new SimpleStringProperty();
  private final BooleanProperty saveButtonDisabled = new SimpleBooleanProperty(true);
  private final ObjectProperty<Schedule> currentSchedule = new SimpleObjectProperty<>();
  private final StringProperty errorMessage = new SimpleStringProperty();

  private final ObservableList<Schedule> availableSchedules = FXCollections.observableArrayList();
  private final ObservableList<Schedule> trainSchedules = FXCollections.observableArrayList();

  private Train selectedTrain;
  private Schedule originalSchedule;

  private final Gson gson = new GsonBuilder().create();
  private static final String HOST = "localhost";
  private static final int PORT = 4892;
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private boolean schedulesModified = false;

  public ModifyTrainVM()
  {
    currentSchedule.addListener((obs, oldVal, newVal) -> {
      if (selectedTrain != null)
      {
        boolean scheduleChanged =
            (originalSchedule == null && newVal != null) || (originalSchedule != null && !originalSchedule.equals(
                newVal));
        saveButtonDisabled.set(!scheduleChanged && !schedulesModified);
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

  public StringProperty getErrorMessageProperty()
  {
    return errorMessage;
  }

  public ObservableList<Schedule> getAvailableSchedules()
  {
    return availableSchedules;
  }

  public ObservableList<Schedule> getTrainSchedules()
  {
    return trainSchedules;
  }

  public void loadTrainData(Train train)
  {
    try
    {
      this.selectedTrain = train;
      if (train != null)
      {
        trainID.set("ID: " + train.getTrainId());
        originalSchedule = train.getSchedule();

        if (originalSchedule != null)
        {
          currentSchedule.set(originalSchedule);
        }
        else
        {
          currentSchedule.set(null);
        }
        loadTrainSchedules();
        loadAllSchedules();
      }
      else
      {
        System.out.println("Warning: Attempted to load null train");
        trainID.set("Error: No train selected");
      }
    }
    catch (Exception e)
    {
      System.out.println("Error in loadTrainData: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void loadAllSchedules()
  {
    try
    {
      System.out.println("Loading all available schedules...");
      Object response = request("schedules", "getAllSchedules", null);
      availableSchedules.clear();
      if (response != null)
      {
        List<Schedule> schedules = gson.fromJson(gson.toJson(response), new TypeToken<List<Schedule>>()
        {
        }.getType());
        if (schedules != null)
        {
          schedules.removeIf(
              schedule -> trainSchedules.stream().anyMatch(ts -> ts.getScheduleId() == schedule.getScheduleId()));

          availableSchedules.addAll(schedules);
        }
      }
      System.out.println("Loaded " + availableSchedules.size() + " available schedules.");
    }
    catch (Exception e)
    {
      System.out.println("Error loading schedules: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void loadTrainSchedules()
  {
    try
    {
      if (selectedTrain == null)
      {
        return;
      }
      int trainId = selectedTrain.getTrainId();
      System.out.println("Loading schedules for train ID: " + trainId);
      try
      {
        Object response = request("schedules", "getSchedulesByTrainId", trainId);
        trainSchedules.clear();
        if (response != null)
        {
          List<Schedule> schedules = gson.fromJson(gson.toJson(response), new TypeToken<List<Schedule>>()
          {
          }.getType());

          if (schedules != null)
          {
            trainSchedules.addAll(schedules);
          }
        }
      }
      catch (Exception e)
      {
        System.out.println("Error loading train schedules: " + e.getMessage());
        e.printStackTrace();
        trainSchedules.clear();
      }
      System.out.println("Loaded " + trainSchedules.size() + " schedules for train " + selectedTrain.getTrainId());
    }
    catch (Exception e)
    {
      System.out.println("Unexpected error in loadTrainSchedules: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public boolean saveChanges()
  {
    if (selectedTrain == null)
    {
      System.out.println("No train selected!");
      errorMessage.set("No train selected");
      return false;
    }
    try
    {
      // Log the current state before saving
      System.out.println("Saving changes for train ID: " + selectedTrain.getTrainId());
      System.out.println(
          "Original schedule: " + (originalSchedule != null ? originalSchedule.getScheduleId() : "null"));
      System.out.println(
          "Current schedule: " + (currentSchedule.get() != null ? currentSchedule.get().getScheduleId() : "null"));

      // First update individual schedules to ensure they're valid
      if (schedulesModified)
      {
        System.out.println("Updating " + trainSchedules.size() + " schedules for train " + selectedTrain.getTrainId());
        for (Schedule schedule : trainSchedules)
        {
          System.out.println("Updating schedule ID: " + schedule.getScheduleId());
          try
          {
            // Validate schedule data before sending
            if (!validateSchedule(schedule))
            {
              errorMessage.set("Invalid schedule data for ID: " + schedule.getScheduleId());
              return false;
            }

            request("schedules", "updateSchedule", schedule);
            System.out.println("Schedule " + schedule.getScheduleId() + " updated successfully.");
          }
          catch (Exception e)
          {
            String errorMsg = "Error updating schedule " + schedule.getScheduleId() + ": " + e.getMessage();
            System.out.println(errorMsg);
            errorMessage.set(errorMsg);
            e.printStackTrace();
            return false;
          }
        }
        System.out.println("All schedules updated successfully.");
        schedulesModified = false;
      }

      // Only after schedules are updated, update the train's current schedule
      if (currentSchedule.get() != originalSchedule)
      {
        selectedTrain.setSchedule(currentSchedule.get());
        System.out.println("Updating train with current schedule: " + (currentSchedule.get() != null ?
            currentSchedule.get().getScheduleId() :
            "null"));
        try
        {
          request("trains", "updateTrain", selectedTrain);
          System.out.println("Train " + selectedTrain.getTrainId() + " current schedule updated successfully.");
        }
        catch (Exception e)
        {
          String errorMsg = "Error updating train: " + e.getMessage();
          System.out.println(errorMsg);
          errorMessage.set(errorMsg);
          e.printStackTrace();
          return false;
        }
      }

      return true;
    }
    catch (Exception e)
    {
      String errorMsg = "Error saving changes: " + e.getMessage();
      System.out.println(errorMsg);
      errorMessage.set(errorMsg);
      e.printStackTrace();
      return false;
    }
  }

  private boolean validateSchedule(Schedule schedule)
  {
    // Check for null values
    if (schedule.getDepartureStation() == null)
    {
      errorMessage.set("Departure station cannot be empty");
      return false;
    }

    if (schedule.getArrivalStation() == null)
    {
      errorMessage.set("Arrival station cannot be empty");
      return false;
    }

    if (schedule.getDepartureDate() == null)
    {
      errorMessage.set("Departure time cannot be empty");
      return false;
    }

    if (schedule.getArrivalDate() == null)
    {
      errorMessage.set("Arrival time cannot be empty");
      return false;
    }

    // Check if departure is before arrival
    try
    {
      LocalDateTime departureTime = ((MyDate) schedule.getDepartureDate()).toLocalDateTime();
      LocalDateTime arrivalTime = ((MyDate) schedule.getArrivalDate()).toLocalDateTime();

      if (departureTime.isAfter(arrivalTime) || departureTime.isEqual(arrivalTime))
      {
        errorMessage.set("Departure time must be before arrival time");
        return false;
      }
    }
    catch (Exception e)
    {
      errorMessage.set("Invalid date format in schedule");
      return false;
    }

    // Verify stations are not the same
    if (schedule.getDepartureStation().getName() == schedule.getArrivalStation().getName())
    {
      errorMessage.set("Departure and arrival stations cannot be the same");
      return false;
    }

    return true;
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
      if (schedule.getDepartureDate() instanceof MyDate)
      {
        MyDate myDate = (MyDate) schedule.getDepartureDate();
        LocalDateTime departureTime = myDate.toLocalDateTime();
        property.set(formatDateTime(departureTime));
      }
      else
      {
        try
        {
          LocalDateTime departureTime = LocalDateTime.of(schedule.getDepartureDate().getYear(),
              schedule.getDepartureDate().getMonth(), schedule.getDepartureDate().getDay(),
              schedule.getDepartureDate().getHour(), schedule.getDepartureDate().getMinute());
          property.set(formatDateTime(departureTime));
        }
        catch (Exception e)
        {
          property.set("Error: Invalid date format");
          e.printStackTrace();
        }
      }
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
      if (schedule.getArrivalDate() instanceof MyDate)
      {
        MyDate myDate = (MyDate) schedule.getArrivalDate();
        LocalDateTime arrivalTime = myDate.toLocalDateTime();
        property.set(formatDateTime(arrivalTime));
      }
      else
      {
        try
        {
          LocalDateTime arrivalTime = LocalDateTime.of(schedule.getArrivalDate().getYear(),
              schedule.getArrivalDate().getMonth(), schedule.getArrivalDate().getDay(),
              schedule.getArrivalDate().getHour(), schedule.getArrivalDate().getMinute());
          property.set(formatDateTime(arrivalTime));
        }
        catch (Exception e)
        {
          property.set("Error: Invalid date format");
          e.printStackTrace();
        }
      }
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

  public boolean updateDepartureStationName(Schedule schedule, String newName)
  {
    try
    {
      if (schedule != null)
      {
        Object response = request("stations", "getStationByName", newName);
        if (response != null)
        {
          Station station = gson.fromJson(gson.toJson(response), Station.class);
          if (station != null)
          {
            schedule.setDepartureStation(station);
            schedulesModified = true;
            saveButtonDisabled.set(false);
            errorMessage.set("");
            return true;
          }
          else
          {
            errorMessage.set("Station not found: " + newName);
            return false;
          }
        }
        else
        {
          errorMessage.set("Failed to fetch station: " + newName);
          return false;
        }
      }
      return false;
    }
    catch (Exception e)
    {
      System.out.println("Error updating departure station: " + e.getMessage());
      e.printStackTrace();
      errorMessage.set("Error updating departure station: " + e.getMessage());
      return false;
    }
  }

  public boolean updateArrivalStationName(Schedule schedule, String newName)
  {
    try
    {
      if (schedule != null)
      {
        Object response = request("stations", "getStationByName", newName);
        if (response != null)
        {
          Station station = gson.fromJson(gson.toJson(response), Station.class);
          if (station != null)
          {
            schedule.setArrivalStation(station);
            schedulesModified = true;
            saveButtonDisabled.set(false);
            errorMessage.set("");
            return true;
          }
          else
          {
            errorMessage.set("Station not found: " + newName);
            return false;
          }
        }
        else
        {
          errorMessage.set("Failed to fetch station: " + newName);
          return false;
        }
      }
      return false;
    }
    catch (Exception e)
    {
      System.out.println("Error updating arrival station: " + e.getMessage());
      e.printStackTrace();
      errorMessage.set("Error updating arrival station: " + e.getMessage());
      return false;
    }
  }

  public void updateDepartureTime(Schedule schedule, LocalDateTime newDateTime)
  {
    try
    {
      if (schedule != null)
      {
        MyDate newDate = new MyDate(newDateTime.getDayOfMonth(), newDateTime.getMonthValue(), newDateTime.getYear(),
            newDateTime.getHour(), newDateTime.getMinute());
        schedule.setDepartureDate(newDate);
        schedulesModified = true;
        saveButtonDisabled.set(false);
      }
    }
    catch (Exception e)
    {
      System.out.println("Error updating departure time: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void updateArrivalTime(Schedule schedule, LocalDateTime newDateTime)
  {
    try
    {
      if (schedule != null)
      {
        MyDate newDate = new MyDate(newDateTime.getDayOfMonth(), newDateTime.getMonthValue(), newDateTime.getYear(),
            newDateTime.getHour(), newDateTime.getMinute());
        schedule.setArrivalDate(newDate);
        schedulesModified = true;
        saveButtonDisabled.set(false);
      }
    }
    catch (Exception e)
    {
      System.out.println("Error updating arrival time: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private Object request(String handler, String action, Object payload) throws Exception
  {
    try (Socket socket = new Socket(HOST, PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
    {

      Request request = new Request(handler, action, payload);
      String jsonRequest = gson.toJson(request);

      // Debug the actual data being sent
      System.out.println("Sending request: " + jsonRequest);

      out.println(jsonRequest);
      String jsonResponse = in.readLine();

      if (jsonResponse == null)
      {
        throw new IOException("No response received from server");
      }

      System.out.println("Received response: " + jsonResponse);

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