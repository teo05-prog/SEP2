package persistance.admin;

import model.entities.MyDate;
import model.entities.Schedule;
import model.entities.Station;

import java.sql.*;
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
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres",
        "141220");
  }

  @Override public List<Schedule> getAllSchedules()
  {
    List<Schedule> schedules = new ArrayList<>();
    String sql = "SELECT * FROM schedule";

    try (var connection = getConnection())
    {
      var statement = connection.prepareStatement(sql);
      ResultSet rs = statement.executeQuery();

      while (rs.next())
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

        Schedule schedule = new Schedule(departureStation, arrivalStation, myDepartureDate, myArrivalDate);
        schedules.add(schedule);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }

    return schedules;
  }

  @Override public Schedule getScheduleById(int scheduleId)
  {
    String sql = "SELECT * FROM schedule WHERE schedule_id = ?";
    Schedule schedule = null;

    try (var connection = getConnection())
    {
      var statement = connection.prepareStatement(sql);
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

        schedule = new Schedule(departureStation, arrivalStation, myDepartureDate, myArrivalDate);
        return schedule;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void createSchedule(Schedule schedule)
  {
    String sql = "INSERT INTO schedule (departureStation, arrivalStation, departureDate, departureTime, arrivalDate, arrivalTime) VALUES (?, ?, ?, ?, ?, ?)";

    try (var connection = getConnection())
    {
      var statement = connection.prepareStatement(sql);
      statement.setString(1, schedule.getDepartureStation().getName());
      statement.setString(2, schedule.getArrivalStation().getName());
      statement.setDate(3, new java.sql.Date(schedule.getDepartureDate().getDate().getTime()));
      statement.setTime(4, Time.valueOf(schedule.getDepartureDate().toString()));
      statement.setDate(5, new java.sql.Date(schedule.getArrivalDate().getDate().getTime()));
      statement.setTime(6, Time.valueOf(schedule.getArrivalDate().toString()));

      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void updateSchedule(Schedule schedule)
  {
    String sql = "UPDATE schedule SET departureStation = ?, arrivalStation = ?, departureDate = ?, departureTime = ?, arrivalDate = ?, arrivalTime = ? WHERE schedule_id = ?";

    try (var connection = getConnection())
    {
      var statement = connection.prepareStatement(sql);
      statement.setString(1, schedule.getDepartureStation().getName());
      statement.setString(2, schedule.getArrivalStation().getName());
      statement.setDate(3, new java.sql.Date(schedule.getDepartureDate().getDate().getTime()));
      statement.setTime(4, Time.valueOf(schedule.getDepartureDate().toString()));
      statement.setDate(5, new java.sql.Date(schedule.getArrivalDate().getDate().getTime()));
      statement.setTime(6, Time.valueOf(schedule.getArrivalDate().toString()));
      //statement.setInt(7, schedule.getScheduleId());

      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void deleteSchedule(Schedule schedule)
  {
    String sql = "DELETE FROM schedule WHERE departureStation = ? AND arrivalStation = ? AND departureDate = ? AND departureTime = ? AND arrivalDate = ? AND arrivalTime = ?";

    try (var connection = getConnection())
    {
      var statement = connection.prepareStatement(sql);
      statement.setString(1, schedule.getDepartureStation().getName());
      statement.setString(2, schedule.getArrivalStation().getName());
      statement.setDate(3, new java.sql.Date(schedule.getDepartureDate().getDate().getTime()));
      statement.setTime(4, Time.valueOf(schedule.getDepartureDate().toString()));
      statement.setDate(5, new java.sql.Date(schedule.getArrivalDate().getDate().getTime()));
      statement.setTime(6, Time.valueOf(schedule.getArrivalDate().toString()));

      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
