package persistance.admin;

import model.entities.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TrainPostgresDAO implements TrainDAO
{
  private static TrainPostgresDAO instance;

  public TrainPostgresDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized TrainPostgresDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new TrainPostgresDAO();
    }
    return instance;
  }

  private static java.sql.Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres",
        "141220");
  }

  @Override public void createTrain(int trainId)
  {
    try (var connection = getConnection())
    {
      String sql = "INSERT INTO train (train_id) VALUES (?)";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, trainId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override public Train readTrainById(int trainId)
  {
    try (var connection = getConnection())
    {
      String sql = "SELECT t.train_id, s.schedule_id, s.departureStation, s.arrivalStation, "
          + "s.departureDate, s.departureTime, s.arrivalDate, s.arrivalTime " + "FROM train t "
          + "LEFT JOIN schedule s ON t.train_id = s.train_id " + "WHERE t.train_id = ?";

      var statement = connection.prepareStatement(sql);
      statement.setInt(1, trainId);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        Train train = new Train(resultSet.getInt("train_id"));

        // Check if the train has a schedule
        if (resultSet.getObject("schedule_id") != null)
        {
          // Create Station objects for departure and arrival
          Station departureStation = new Station(resultSet.getString("departureStation"));
          Station arrivalStation = new Station(resultSet.getString("arrivalStation"));

          // Create MyDate objects for departure and arrival
          MyDate departureDate = null;
          MyDate arrivalDate = null;

          if (resultSet.getDate("departureDate") != null)
          {
            departureDate = new MyDate(resultSet.getDate("departureDate"));
            if (resultSet.getTime("departureTime") != null)
            {
              Time departureTime = resultSet.getTime("departureTime");
              departureDate.setHour(departureTime.toLocalTime().getHour());
              departureDate.setMinute(departureTime.toLocalTime().getMinute());
            }
          }
          else
          {
            departureDate = MyDate.today(); // Fallback
          }

          if (resultSet.getDate("arrivalDate") != null)
          {
            arrivalDate = new MyDate(resultSet.getDate("arrivalDate"));
            if (resultSet.getTime("arrivalTime") != null)
            {
              Time arrivalTime = resultSet.getTime("arrivalTime");
              arrivalDate.setHour(arrivalTime.toLocalTime().getHour());
              arrivalDate.setMinute(arrivalTime.toLocalTime().getMinute());
            }
          }
          else
          {
            arrivalDate = MyDate.today(); // Fallback
          }

          // Create the schedule with the new implementation
          Schedule schedule = new Schedule(departureStation, arrivalStation, departureDate, arrivalDate);

          // Use reflection to set the schedule since the field might be private
          try
          {
            java.lang.reflect.Field scheduleField = Train.class.getDeclaredField("schedule");
            scheduleField.setAccessible(true);
            scheduleField.set(train, schedule);
          }
          catch (NoSuchFieldException | IllegalAccessException e)
          {
            e.printStackTrace();
          }
        }
        return train;
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override public void deleteTrain(int trainId)
  {
    try (var connection = getConnection())
    {
      // Delete tickets associated with the train
      String deleteTicketsSQL = "DELETE FROM ticket WHERE train_id = ?";
      var ticketStatement = connection.prepareStatement(deleteTicketsSQL);
      ticketStatement.setInt(1, trainId);
      ticketStatement.executeUpdate();

      // Delete schedule entries associated with the train
      String deleteScheduleSQL = "DELETE FROM schedule WHERE train_id = ?";
      var scheduleStatement = connection.prepareStatement(deleteScheduleSQL);
      scheduleStatement.setInt(1, trainId);
      scheduleStatement.executeUpdate();

      // Delete carriages associated with the train
      String deleteCarriagesSQL = "DELETE FROM carriage WHERE train_id = ?";
      var carriageStatement = connection.prepareStatement(deleteCarriagesSQL);
      carriageStatement.setInt(1, trainId);
      carriageStatement.executeUpdate();

      // Finally delete the train itself
      String deleteTrainSQL = "DELETE FROM train WHERE train_id = ?";
      var trainStatement = connection.prepareStatement(deleteTrainSQL);
      trainStatement.setInt(1, trainId);
      trainStatement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void updateTrain(Train train)
  {
    try (var connection = getConnection())
    {
      String sql = "UPDATE train SET train_id = ? WHERE train_id = ?";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, train.getTrainId());
      statement.setInt(2, train.getTrainId());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override public TrainList allTrains()
  {
    TrainList trainList = new TrainList();

    try (var connection = getConnection())
    {
      String sql = "SELECT t.train_id, s.schedule_id, s.departureStation, s.arrivalStation, "
          + "s.departureDate, s.departureTime, s.arrivalDate, s.arrivalTime " + "FROM train t "
          + "LEFT JOIN schedule s ON t.train_id = s.train_id " + "ORDER BY t.train_id";

      var statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      // Keep track of which trains we've already added
      ArrayList<Integer> processedTrainIds = new ArrayList<>();

      while (resultSet.next())
      {
        int trainId = resultSet.getInt("train_id");

        // Skip if we've already processed this train
        if (processedTrainIds.contains(trainId))
        {
          continue;
        }

        processedTrainIds.add(trainId);
        Train train = new Train(trainId);

        // Check if the train has a schedule
        if (resultSet.getObject("schedule_id") != null)
        {
          // Create Station objects for departure and arrival
          Station departureStation = new Station(resultSet.getString("departureStation"));
          Station arrivalStation = new Station(resultSet.getString("arrivalStation"));

          // Create MyDate objects for departure and arrival
          MyDate departureDate = null;
          MyDate arrivalDate = null;

          if (resultSet.getDate("departureDate") != null)
          {
            departureDate = new MyDate(resultSet.getDate("departureDate"));
            if (resultSet.getTime("departureTime") != null)
            {
              Time departureTime = resultSet.getTime("departureTime");
              departureDate.setHour(departureTime.toLocalTime().getHour());
              departureDate.setMinute(departureTime.toLocalTime().getMinute());
            }
          }
          else
          {
            departureDate = MyDate.today(); // Fallback
          }

          if (resultSet.getDate("arrivalDate") != null)
          {
            arrivalDate = new MyDate(resultSet.getDate("arrivalDate"));
            if (resultSet.getTime("arrivalTime") != null)
            {
              Time arrivalTime = resultSet.getTime("arrivalTime");
              arrivalDate.setHour(arrivalTime.toLocalTime().getHour());
              arrivalDate.setMinute(arrivalTime.toLocalTime().getMinute());
            }
          }
          else
          {
            arrivalDate = MyDate.today(); // Fallback
          }

          // Create the schedule with the new implementation
          Schedule schedule = new Schedule(departureStation, arrivalStation, departureDate, arrivalDate);

          // Use reflection to set the schedule since the field might be private
          try
          {
            java.lang.reflect.Field scheduleField = Train.class.getDeclaredField("schedule");
            scheduleField.setAccessible(true);
            scheduleField.set(train, schedule);
          }
          catch (NoSuchFieldException | IllegalAccessException e)
          {
            e.printStackTrace();
          }
        }
        trainList.addTrain(train);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return trainList;
  }

  @Override public List<Train> getAllTrains()
  {
    List<Train> trains = new ArrayList<>();
    try (var connection = getConnection())
    {
      String sql = "SELECT * FROM train";
      var statement = connection.prepareStatement(sql);
      ResultSet rs = statement.executeQuery();

      while (rs.next())
      {
        Train train = new Train(rs.getInt("train_id"));
        trains.add(train);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return trains;
  }
}
