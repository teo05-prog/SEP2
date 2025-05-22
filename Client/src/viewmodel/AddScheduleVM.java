package viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import model.entities.TrainList;
import persistance.admin.TrainDAO;
import persistance.admin.TrainPostgresDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddScheduleVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final StringProperty trainID = new SimpleStringProperty();
  private final StringProperty departureStation = new SimpleStringProperty();
  private final StringProperty arrivalStation = new SimpleStringProperty();
  private final StringProperty departureTime = new SimpleStringProperty();
  private final StringProperty arrivalTime = new SimpleStringProperty();
  private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> arrivalDate = new SimpleObjectProperty<>();
  private final BooleanProperty addButtonDisabled = new SimpleBooleanProperty(true);
  private final BooleanProperty addTrainSuccess = new SimpleBooleanProperty(false);

  public AddScheduleVM()
  {
    addButtonDisabled.bind(trainID.isEmpty()
        .or(departureStation.isEmpty())
        .or(arrivalStation.isEmpty())
        .or(departureTime.isEmpty())
        .or(arrivalTime.isEmpty())
        .or(departureDate.isNull())
        .or(arrivalDate.isNull()));
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public StringProperty trainIDProperty()
  {
    return trainID;
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

  public ObservableValue<Boolean> getAddTrainButtonDisabledProperty()
  {
    return addButtonDisabled;
  }

  public boolean isAddTrainSuccess()
  {
    return addTrainSuccess.get();
  }

  public void generateTrainID() throws SQLException
  {
    try
    {
      TrainDAO trainDAO = TrainPostgresDAO.getInstance();
      TrainList allTrains = trainDAO.allTrains();
      int nextId = allTrains.size() + 1;
      trainID.set(String.valueOf(nextId));
    }
    catch (Exception e)
    {
      System.err.println("Error generating train ID: " + e.getMessage());
      trainID.set("1");
    }
  }

  public void addTrain()
  {
    // Clear any previous messages
    message.set("");
    addTrainSuccess.set(false);

    // Validate all fields (though button should be disabled if any are empty)
    if (departureStation.get() == null || departureStation.get().isEmpty())
    {
      message.set("Departure station cannot be empty.");
      return;
    }
    if (arrivalStation.get() == null || arrivalStation.get().isEmpty())
    {
      message.set("Arrival station cannot be empty.");
      return;
    }
    if (departureTime.get() == null || departureTime.get().isEmpty())
    {
      message.set("Departure time cannot be empty.");
      return;
    }
    if (arrivalTime.get() == null || arrivalTime.get().isEmpty())
    {
      message.set("Arrival time cannot be empty.");
      return;
    }
    if (departureDate.get() == null)
    {
      message.set("Departure date cannot be empty.");
      return;
    }
    if (arrivalDate.get() == null)
    {
      message.set("Arrival date cannot be empty.");
      return;
    }
    if (arrivalDate.get().isBefore(departureDate.get()))
    {
      message.set("Arrival date cannot be before departure date.");
      return;
    }
    if (arrivalDate.get().equals(departureDate.get()))
    {
      if (isTimeAfterOrEqual(departureTime.get(), arrivalTime.get()))
      {
        message.set("Arrival time must be after departure time on the same date.");
        return;
      }
    }
    if (departureStation.get().equals(arrivalStation.get()))
    {
      message.set("Departure and arrival stations cannot be the same.");
      return;
    }

    try
    {
      addTrainToDatabase();
      addTrainSuccess.set(true);
      message.set("Train added successfully!");
    }
    catch (Exception e)
    {
      message.set("Error adding train: " + e.getMessage());
    }
  }

  private boolean isTimeAfterOrEqual(String time1, String time2)
  {
    // Convert times to minutes for comparison
    String[] time1Parts = time1.split(":");
    String[] time2Parts = time2.split(":");

    int time1Minutes = Integer.parseInt(time1Parts[0]) * 60 + Integer.parseInt(time1Parts[1]);
    int time2Minutes = Integer.parseInt(time2Parts[0]) * 60 + Integer.parseInt(time2Parts[1]);

    return time1Minutes >= time2Minutes;
  }

  private void addTrainToDatabase()
  {
    try
    {
      // Use services instead of DAOs directly for better architecture
      var trainService = getTrainService();
      var scheduleService = getScheduleService();

      int trainIdInt = Integer.parseInt(trainID.get());

      // First create the train
      trainService.createTrain(trainIdInt);

      // Convert LocalDate and time strings to MyDate objects
      model.entities.MyDate departureMyDate = convertToMyDate(departureDate.get(), departureTime.get());
      model.entities.MyDate arrivalMyDate = convertToMyDate(arrivalDate.get(), arrivalTime.get());

      // Create Station objects
      model.entities.Station depStation = new model.entities.Station(departureStation.get());
      model.entities.Station arrStation = new model.entities.Station(arrivalStation.get());

      // Create schedule
      model.entities.Schedule schedule = new model.entities.Schedule(
          0, // schedule ID will be auto-generated by database
          depStation,
          arrStation,
          departureMyDate,
          arrivalMyDate
      );

      // Create the schedule
      scheduleService.createSchedule(schedule);

      System.out.println("Successfully added train to database:");
      System.out.println("Train ID: " + trainID.get());
      System.out.println("From: " + departureStation.get() + " at " + departureTime.get() + " on " + departureDate.get());
      System.out.println("To: " + arrivalStation.get() + " at " + arrivalTime.get() + " on " + arrivalDate.get());
    }
    catch (Exception e)
    {
      System.err.println("Error adding train to database: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to add train to database: " + e.getMessage());
    }
  }

  // Helper methods to get services - you might need to adjust this based on your architecture
  private services.admin.TrainService getTrainService()
  {
    try
    {
      return new services.admin.TrainServiceImpl(persistance.admin.TrainPostgresDAO.getInstance());
    }
    catch (Exception e)
    {
      throw new RuntimeException("Failed to get TrainService", e);
    }
  }

  private services.admin.ScheduleService getScheduleService()
  {
    try
    {
      return new services.admin.ScheduleServiceImpl(persistance.admin.SchedulePostgresDAO.getInstance());
    }
    catch (Exception e)
    {
      throw new RuntimeException("Failed to get ScheduleService", e);
    }
  }

  private model.entities.MyDate convertToMyDate(java.time.LocalDate date, String timeString)
  {
    // Create MyDate from LocalDate
    model.entities.MyDate myDate = new model.entities.MyDate(
        date.getDayOfMonth(),
        date.getMonthValue(),
        date.getYear()
    );

    // Parse and set time
    String[] timeParts = timeString.split(":");
    int hour = Integer.parseInt(timeParts[0]);
    int minute = Integer.parseInt(timeParts[1]);

    myDate.setHour(hour);
    myDate.setMinute(minute);

    return myDate;
  }
}