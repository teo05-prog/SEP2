package persistance.seat;

import utilities.LogLevel;
import utilities.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatPostgresDAO implements SeatDAO
{
  private static SeatPostgresDAO instance;
  private final Logger logger;

  private SeatPostgresDAO(Logger logger)
  {
    this.logger = logger;
  }

  public static synchronized void init(Logger logger)
  {
    if (instance == null)
    {
      instance = new SeatPostgresDAO(logger);
    }
  }

  public static synchronized SeatPostgresDAO getInstance()
  {
    if (instance == null)
    {
      throw new IllegalStateException("SeatPostgresDAO not initialized");
    }
    return instance;
  }

  private Connection getConnection() throws SQLException
  {
    //return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres", "14012004");
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres",
        "141220");
  }

  //returns a list of booked seat number for a given train
  @Override public List<Integer> getBookedSeatsForTrain(int trainId)
  {
    List<Integer> bookedSeats = new ArrayList<>();

    String sql = "SELECT seat_number FROM ticket WHERE train_id =?";

    try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, trainId);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        String seatStr = resultSet.getString("seat_number");
        try
        {
          bookedSeats.add(Integer.parseInt(seatStr));
        }
        catch (NumberFormatException e)
        {
          logger.log("Invalid seat number format: " + seatStr, LogLevel.ERROR);
        }
      }
      logger.log("Loaded: " + bookedSeats.size() + " booked seats for train ID" + trainId, LogLevel.INFO);
    }
    catch (SQLException e)
    {
      logger.log("Error fetching booked seats for train " + trainId + ": " + e.getMessage(), LogLevel.ERROR);
    }
    return bookedSeats;
  }
}
