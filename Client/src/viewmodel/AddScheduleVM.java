package viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AddScheduleDTO;
import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import javafx.beans.property.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Map;

public class AddScheduleVM
{
  private final StringProperty message = new SimpleStringProperty();

  private final StringProperty departureStation = new SimpleStringProperty("");
  private final StringProperty arrivalStation = new SimpleStringProperty("");

  private final StringProperty departureTime = new SimpleStringProperty("");
  private final StringProperty arrivalTime = new SimpleStringProperty("");

  private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> arrivalDate = new SimpleObjectProperty<>();

  private final BooleanProperty addButtonDisabled = new SimpleBooleanProperty(true);
  private final BooleanProperty addScheduleSuccess = new SimpleBooleanProperty(false);

  private final Gson gson = new GsonBuilder().create();
  private static final String HOST = "localhost";
  private static final int PORT = 4892;

  private int trainId;

  public AddScheduleVM()
  {
    LocalDate today = LocalDate.now();
    departureDate.set(today);
    arrivalDate.set(today);

    addButtonDisabled.bind(
        departureStation.isEmpty().or(arrivalStation.isEmpty()).or(departureTime.isEmpty()).or(arrivalTime.isEmpty())
            .or(departureDate.isNull()).or(arrivalDate.isNull()));
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public StringProperty departureStationProperty()
  {
    return departureStation;
  }

  public StringProperty arrivalStationProperty()
  {
    return arrivalStation;
  }

  public StringProperty departureTimeProperty()
  {
    return departureTime;
  }

  public StringProperty arrivalTimeProperty()
  {
    return arrivalTime;
  }

  public ObjectProperty<LocalDate> arrivalDateProperty()
  {
    return arrivalDate;
  }

  public ObjectProperty<LocalDate> departureDateProperty()
  {
    return departureDate;
  }

  public BooleanProperty getAddButtonDisabledProperty()
  {
    return addButtonDisabled;
  }

  public int getTrainId()
  {
    return this.trainId;
  }

  public void setTrainId(int trainId)
  {
    this.trainId = trainId;
    if (trainId <= 0)
    {
      System.err.println("WARNING: Train ID is zero or negative: " + trainId);
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
    catch (Exception e)
    {
      throw new Exception("Network error: " + e.getMessage());
    }
  }

  public boolean addSchedule()
  {
    try
    {
      if (this.trainId <= 0)
      {
        System.err.println("ERROR: Invalid train ID (0 or negative). Cannot proceed with adding schedule.");
        message.set("Invalid train ID: " + this.trainId + ". Please try again.");
        return false;
      }

      Object response = request("addSchedule", "getNextScheduleId", null);

      if (response == null)
      {
        message.set("Error getting next schedule ID from server");
        return false;
      }

      String jsonResponse = gson.toJson(response);
      Map<String, Object> responseMap = gson.fromJson(jsonResponse, Map.class);

      Double nextScheduleIdDouble = (Double) responseMap.get("nextScheduleId");
      if (nextScheduleIdDouble == null)
      {
        message.set("Server returned invalid schedule ID");
        return false;
      }
      int nextScheduleId = nextScheduleIdDouble.intValue();

      String departureStationString = departureStation.get();
      String arrivalStationString = arrivalStation.get();
      String departureTimeString = departureTime.get();
      String arrivalTimeString = arrivalTime.get();
      LocalDate departureDateValue = departureDate.get();
      LocalDate arrivalDateValue = arrivalDate.get();

      String departureDateString = String.format("%d-%02d-%02d", departureDateValue.getYear(),
          departureDateValue.getMonthValue(), departureDateValue.getDayOfMonth());

      String arrivalDateString = String.format("%d-%02d-%02d", arrivalDateValue.getYear(),
          arrivalDateValue.getMonthValue(), arrivalDateValue.getDayOfMonth());

      AddScheduleDTO scheduleDTO = new AddScheduleDTO(nextScheduleId, this.trainId, departureStationString,
          arrivalStationString, departureDateString, departureTimeString, arrivalDateString, arrivalTimeString);

      Object addResponse = request("addSchedule", "schedule", scheduleDTO);

      if (addResponse != null)
      {
        String addJsonResponse = gson.toJson(addResponse);
        Map<String, Object> addResponseMap = gson.fromJson(addJsonResponse, Map.class);

        Boolean success = (Boolean) addResponseMap.get("success");
        if (success != null && success)
        {
          message.set("Schedule added successfully!");
          addScheduleSuccess.set(true);
          return true;
        }
        else
        {
          String errorMessage = (String) addResponseMap.get("message");
          message.set(errorMessage != null ? errorMessage : "Failed to add schedule");
          return false;
        }
      }
      else
      {
        message.set("No response received from server");
        return false;
      }
    }
    catch (Exception e)
    {
      System.err.println("Exception in addSchedule: " + e.getMessage());
      e.printStackTrace();
      message.set("Error adding schedule: " + e.getMessage());
      return false;
    }
  }
}