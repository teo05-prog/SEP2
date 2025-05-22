package persistance.admin;

import model.entities.MyDate;
import model.entities.Schedule;
import model.entities.Station;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchedulePostgresDAO implements ScheduleDAO
{
  private static SchedulePostgresDAO instance;

  public SchedulePostgresDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized SchedulePostgresDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new SchedulePostgresDAO();
    }
    return instance;
  }

  private static java.sql.Connection getConnection() throws SQLException
  {
    Connection connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres", "141220");
    connection.setAutoCommit(true); // Ensure auto-commit is enabled
    return connection;
  }

  @Override public List<Schedule> getAllSchedules()
  {
    List<Schedule> schedules = new ArrayList<>();
    String sql = "SELECT * FROM schedule";

    try (var connection = getConnection(); var statement = connection.prepareStatement(sql))
    {
      ResultSet rs = statement.executeQuery();

      while (rs.next())
      {
        int scheduleId = rs.getInt("schedule_id");
        Station departureStation = new Station(rs.getString("departureStation"));
        Station arrivalStation = new Station(rs.getString("arrivalStation"));

        Date departureDate = rs.getDate("departureDate");
        Time departureTime = rs.getTime("departureTime");
        Date arrivalDate = rs.getDate("arrivalDate");
        Time arrivalTime = rs.getTime("arrivalTime");

        MyDate myDepartureDate = new MyDate((java.sql.Date) departureDate);
        myDepartureDate.setHour(departureTime.toLocalTime().getHour());
        myDepartureDate.setMinute(departureTime.toLocalTime().getMinute());

        MyDate myArrivalDate = new MyDate((java.sql.Date) arrivalDate);
        myArrivalDate.setHour(arrivalTime.toLocalTime().getHour());
        myArrivalDate.setMinute(arrivalTime.toLocalTime().getMinute());

        Schedule schedule = new Schedule(scheduleId, departureStation, arrivalStation, myDepartureDate, myArrivalDate);
        schedules.add(schedule);
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error getting all schedules: " + e.getMessage());
      e.printStackTrace();
    }

    return schedules;
  }

  @Override public Schedule getScheduleById(int scheduleId)
  {
    String sql = "SELECT * FROM schedule WHERE schedule_id = ?";
    Schedule schedule = null;

    try (var connection = getConnection(); var statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, scheduleId);
      ResultSet rs = statement.executeQuery();

      if (rs.next())
      {
        Station departureStation = new Station(rs.getString("departureStation"));
        Station arrivalStation = new Station(rs.getString("arrivalStation"));

        Date departureDate = rs.getDate("departureDate");
        Time departureTime = rs.getTime("departureTime");
        Date arrivalDate = rs.getDate("arrivalDate");
        Time arrivalTime = rs.getTime("arrivalTime");

        MyDate myDepartureDate = new MyDate((java.sql.Date) departureDate);
        myDepartureDate.setHour(departureTime.toLocalTime().getHour());
        myDepartureDate.setMinute(departureTime.toLocalTime().getMinute());

        MyDate myArrivalDate = new MyDate((java.sql.Date) arrivalDate);
        myArrivalDate.setHour(arrivalTime.toLocalTime().getHour());
        myArrivalDate.setMinute(arrivalTime.toLocalTime().getMinute());

        schedule = new Schedule(scheduleId, departureStation, arrivalStation, myDepartureDate, myArrivalDate);
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error getting schedule by ID: " + e.getMessage());
      e.printStackTrace();
    }
    return schedule;
  }

  @Override public void createSchedule(Schedule schedule)
  {
    String sql = "INSERT INTO schedule (departureStation, arrivalStation, departureDate, departureTime, arrivalDate, arrivalTime) VALUES (?, ?, ?, ?, ?, ?)";

    Connection connection = null;
    PreparedStatement statement = null;

    try
    {
      connection = getConnection();
      statement = connection.prepareStatement(sql);

      statement.setString(1, schedule.getDepartureStation().getName());
      statement.setString(2, schedule.getArrivalStation().getName());
      statement.setDate(3, new java.sql.Date(schedule.getDepartureDate().getDate().getTime()));

      // Fix time conversion
      LocalTime depTime = LocalTime.of(schedule.getDepartureDate().getHour(), schedule.getDepartureDate().getMinute());
      statement.setTime(4, Time.valueOf(depTime));

      statement.setDate(5, new java.sql.Date(schedule.getArrivalDate().getDate().getTime()));

      LocalTime arrTime = LocalTime.of(schedule.getArrivalDate().getHour(), schedule.getArrivalDate().getMinute());
      statement.setTime(6, Time.valueOf(arrTime));

      int rowsAffected = statement.executeUpdate();
      System.out.println("Create schedule: " + rowsAffected + " rows affected");

      if (rowsAffected == 0)
      {
        throw new SQLException("Creating schedule failed, no rows affected.");
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error creating schedule: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to create schedule", e);
    }
    finally
    {
      closeResources(statement, connection);
    }
  }

  @Override public void updateSchedule(Schedule schedule)
  {
    String sql = "UPDATE schedule SET departureStation = ?, arrivalStation = ?, departureDate = ?, departureTime = ?, arrivalDate = ?, arrivalTime = ? WHERE schedule_id = ?";

    Connection connection = null;
    PreparedStatement statement = null;

    try
    {
      connection = getConnection();
      statement = connection.prepareStatement(sql);

      statement.setString(1, schedule.getDepartureStation().getName());
      statement.setString(2, schedule.getArrivalStation().getName());
      statement.setDate(3, new java.sql.Date(schedule.getDepartureDate().getDate().getTime()));

      // Fix time conversion
      LocalTime depTime = LocalTime.of(schedule.getDepartureDate().getHour(), schedule.getDepartureDate().getMinute());
      statement.setTime(4, Time.valueOf(depTime));

      statement.setDate(5, new java.sql.Date(schedule.getArrivalDate().getDate().getTime()));

      LocalTime arrTime = LocalTime.of(schedule.getArrivalDate().getHour(), schedule.getArrivalDate().getMinute());
      statement.setTime(6, Time.valueOf(arrTime));

      statement.setInt(7, schedule.getScheduleId());

      int rowsAffected = statement.executeUpdate();
      System.out.println(
          "Update schedule: " + rowsAffected + " rows affected for schedule ID: " + schedule.getScheduleId());

      if (rowsAffected == 0)
      {
        throw new SQLException("Updating schedule failed, no rows affected. Schedule ID: " + schedule.getScheduleId());
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error updating schedule: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to update schedule", e);
    }
    finally
    {
      closeResources(statement, connection);
    }
  }

  @Override public void deleteSchedule(Schedule schedule)
  {
    String sql = "DELETE FROM schedule WHERE schedule_id = ?";

    Connection connection = null;
    PreparedStatement statement = null;

    try
    {
      connection = getConnection();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, schedule.getScheduleId());

      int rowsAffected = statement.executeUpdate();
      System.out.println(
          "Delete schedule: " + rowsAffected + " rows affected for schedule ID: " + schedule.getScheduleId());

      if (rowsAffected == 0)
      {
        throw new SQLException("Deleting schedule failed, no rows affected. Schedule ID: " + schedule.getScheduleId());
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error deleting schedule: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to delete schedule", e);
    }
    finally
    {
      closeResources(statement, connection);
    }
  }

  @Override public void assignScheduleToTrain(int scheduleId, int trainId)
  {
    String sql = "UPDATE schedule SET train_id = ? WHERE schedule_id = ?";

    Connection connection = null;
    PreparedStatement statement = null;

    try
    {
      connection = getConnection();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, trainId);
      statement.setInt(2, scheduleId);

      int rowsAffected = statement.executeUpdate();
      System.out.println(
          "Assign schedule to train: " + rowsAffected + " rows affected. Schedule ID: " + scheduleId + ", Train ID: "
              + trainId);

      if (rowsAffected == 0)
      {
        throw new SQLException("Assigning schedule to train failed, no rows affected. Schedule ID: " + scheduleId);
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error assigning schedule to train: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to assign schedule to train", e);
    }
    finally
    {
      closeResources(statement, connection);
    }
  }

  @Override public void removeScheduleFromTrain(int scheduleId)
  {
    String sql = "UPDATE schedule SET train_id = NULL WHERE schedule_id = ?";

    Connection connection = null;
    PreparedStatement statement = null;

    try
    {
      connection = getConnection();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, scheduleId);

      int rowsAffected = statement.executeUpdate();
      System.out.println(
          "Remove schedule from train: " + rowsAffected + " rows affected for schedule ID: " + scheduleId);

      if (rowsAffected == 0)
      {
        throw new SQLException("Removing schedule from train failed, no rows affected. Schedule ID: " + scheduleId);
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error removing schedule from train: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to remove schedule from train", e);
    }
    finally
    {
      closeResources(statement, connection);
    }
  }

  @Override public List<Schedule> getSchedulesByTrainId(int trainId)
  {
    List<Schedule> schedules = new ArrayList<>();
    String sql = "SELECT * FROM schedule WHERE train_id = ?";

    try (var connection = getConnection(); var statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, trainId);
      ResultSet rs = statement.executeQuery();

      while (rs.next())
      {
        int scheduleId = rs.getInt("schedule_id");
        Station departureStation = new Station(rs.getString("departureStation"));
        Station arrivalStation = new Station(rs.getString("arrivalStation"));

        Date departureDate = rs.getDate("departureDate");
        Time departureTime = rs.getTime("departureTime");
        Date arrivalDate = rs.getDate("arrivalDate");
        Time arrivalTime = rs.getTime("arrivalTime");

        MyDate myDepartureDate = new MyDate((java.sql.Date) departureDate);
        myDepartureDate.setHour(departureTime.toLocalTime().getHour());
        myDepartureDate.setMinute(departureTime.toLocalTime().getMinute());

        MyDate myArrivalDate = new MyDate((java.sql.Date) arrivalDate);
        myArrivalDate.setHour(arrivalTime.toLocalTime().getHour());
        myArrivalDate.setMinute(arrivalTime.toLocalTime().getMinute());

        Schedule schedule = new Schedule(scheduleId, departureStation, arrivalStation, myDepartureDate, myArrivalDate);
        schedules.add(schedule);
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error getting schedules by train ID: " + e.getMessage());
      e.printStackTrace();
    }

    return schedules;
  }

  private void closeResources(PreparedStatement statement, Connection connection)
  {
    if (statement != null)
    {
      try
      {
        statement.close();
      }
      catch (SQLException e)
      {
        System.err.println("Error closing statement: " + e.getMessage());
      }
    }
    if (connection != null)
    {
      try
      {
        connection.close();
      }
      catch (SQLException e)
      {
        System.err.println("Error closing connection: " + e.getMessage());
      }
    }
  }
}