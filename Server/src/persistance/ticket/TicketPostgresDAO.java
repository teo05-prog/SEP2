package persistance.ticket;

import model.entities.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketPostgresDAO implements TicketDAO
{
  private static TicketPostgresDAO instance;

  public TicketPostgresDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized TicketPostgresDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new TicketPostgresDAO();
    }
    return instance;
  }

  private static java.sql.Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres",
        "141220");
  }

  @Override public void createSeatAndBicycleTicket(int ticketID, Bicycle bicycleSeat, Seat seatId,
      Schedule scheduleId, String email)
  {
    try (var connection = getConnection())
    {
      String sql = "INSERT INTO ticket (ticket_id, schedule_id, bicycle_number, seat_number, user_email) VALUES (?, ?, ?, ?, ?)";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, ticketID);
      statement.setInt(2, scheduleId.getScheduleId());
      statement.setInt(3, bicycleSeat.getBicycleSeatId());
      statement.setInt(4, seatId.getSeatId());
      statement.setString(5, email);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.err.println("Error creating seat and bicycle ticket: " + e.getMessage());
      throw new RuntimeException("Database error while creating ticket", e);
    }
  }

  @Override public void createSeatTicket(int ticketID, Seat seatId, Schedule scheduleId, String email)
  {
    try (var connection = getConnection())
    {
      String sql = "INSERT INTO ticket (ticket_id, schedule_id, seat_number, user_email) VALUES (?, ?, ?, ?)";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, ticketID);
      statement.setInt(2, scheduleId.getScheduleId());
      statement.setInt(3, seatId.getSeatId());
      statement.setString(4, email);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.err.println("Error creating seat ticket: " + e.getMessage());
      throw new RuntimeException("Database error while creating ticket", e);
    }
  }

  @Override public void createBicycleTicket(int ticketID, Bicycle bicycleSeat, Schedule scheduleId,
      String email)
  {
    try (var connection = getConnection())
    {
      String sql = "INSERT INTO ticket (ticket_id, schedule_id, bicycle_number, user_email) VALUES (?, ?, ?, ?)";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, ticketID);
      statement.setInt(2, scheduleId.getScheduleId());
      statement.setInt(3, bicycleSeat.getBicycleSeatId());
      statement.setString(4, email);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.err.println("Error creating bicycle ticket: " + e.getMessage());
      throw new RuntimeException("Database error while creating ticket", e);
    }
  }

  @Override public void createTicket(int ticketID, Schedule scheduleId, String email)
  {
    try (var connection = getConnection())
    {
      String sql = "INSERT INTO ticket (ticket_id, schedule_id, user_email) VALUES (?, ?, ?)";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, ticketID);
      statement.setInt(2, scheduleId.getScheduleId());
      statement.setString(3, email);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.err.println("Error creating ticket: " + e.getMessage());
      throw new RuntimeException("Database error while creating ticket", e);
    }
  }

  @Override public void updateTicket(Ticket ticket)
  {
    try (var connection = getConnection())
    {
      StringBuilder sqlBuilder = new StringBuilder("UPDATE ticket SET schedule_id = ?, user_email = ?");

      // Check if seat is being updated
      if (ticket.getSeatId() != null)
      {
        sqlBuilder.append(", seat_number = ?");
      }
      // Check if bicycle is being updated
      if (ticket.getBicycleSeat() != null)
      {
        sqlBuilder.append(", bicycle_number = ?");
      }
      sqlBuilder.append(" WHERE ticket_id = ?");

      var statement = connection.prepareStatement(sqlBuilder.toString());
      statement.setInt(1, ticket.getScheduleId().getScheduleId());
      statement.setString(2, ticket.getEmail());

      int paramIndex = 3;
      // Set seat parameter if needed
      if (ticket.getSeatId() != null)
      {
        statement.setInt(paramIndex++, ticket.getSeatId().getSeatId());
      }
      // Set bicycle parameter if needed
      if (ticket.getBicycleSeat() != null)
      {
        statement.setInt(paramIndex++, ticket.getBicycleSeat().getBicycleSeatId());
      }
      statement.setInt(paramIndex, ticket.getTicketID());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.err.println("Error updating ticket: " + e.getMessage());
      throw new RuntimeException("Database error while updating ticket", e);
    }
  }

  @Override public void deleteTicket(int ticketId)
  {
    try (var connection = getConnection())
    {
      String sql = "DELETE FROM ticket WHERE ticket_id = ?";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, ticketId);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      System.err.println("Error deleting ticket: " + e.getMessage());
      throw new RuntimeException("Database error while deleting ticket", e);
    }
  }

  @Override public List<Ticket> getAllTickets()
  {
    try (var connection = getConnection())
    {
      String sql = "SELECT t.*, "
          + "s.departureStation, s.arrivalStation, s.departureDate, s.departureTime, s.arrivalDate, s.arrivalTime, "
          + "s.train_id "
          + "FROM ticket t "
          + "JOIN schedule s ON t.schedule_id = s.schedule_id";
      var statement = connection.prepareStatement(sql);
      var resultSet = statement.executeQuery();
      List<Ticket> tickets = new ArrayList<>();

      while (resultSet.next())
      {
        tickets.add(extractTicketFromResultSet(resultSet));
      }
      return tickets;
    }
    catch (SQLException e)
    {
      System.err.println("Error retrieving all tickets: " + e.getMessage());
      throw new RuntimeException("Database error while retrieving tickets", e);
    }
  }

  @Override public Ticket getTicketById(int ticketId)
  {
    try (var connection = getConnection())
    {
      String sql = "SELECT t.*, "
          + "s.departureStation, s.arrivalStation, s.departureDate, s.departureTime, s.arrivalDate, s.arrivalTime, "
          + "s.train_id "
          + "FROM ticket t "
          + "JOIN schedule s ON t.schedule_id = s.schedule_id "
          + "WHERE t.ticket_id = ?";
      var statement = connection.prepareStatement(sql);
      statement.setInt(1, ticketId);
      var resultSet = statement.executeQuery();

      if (resultSet.next())
      {
        return extractTicketFromResultSet(resultSet);
      }
      return null;
    }
    catch (SQLException e)
    {
      System.err.println("Error retrieving ticket by ID: " + e.getMessage());
      throw new RuntimeException("Database error while retrieving ticket", e);
    }
  }

  @Override public List<Ticket> getTicketsByEmail(String email)
  {
    try (var connection = getConnection())
    {
      String sql = "SELECT t.*, "
          + "s.departureStation, s.arrivalStation, s.departureDate, s.departureTime, s.arrivalDate, s.arrivalTime, "
          + "s.train_id "
          + "FROM ticket t "
          + "JOIN schedule s ON t.schedule_id = s.schedule_id "
          + "WHERE t.user_email = ?";
      var statement = connection.prepareStatement(sql);
      statement.setString(1, email);
      var resultSet = statement.executeQuery();
      List<Ticket> tickets = new ArrayList<>();

      while (resultSet.next())
      {
        tickets.add(extractTicketFromResultSet(resultSet));
      }
      return tickets;
    }
    catch (SQLException e)
    {
      System.err.println("Error retrieving tickets by user email: " + e.getMessage());
      throw new RuntimeException("Database error while retrieving user tickets", e);
    }
  }

  private Ticket extractTicketFromResultSet(ResultSet resultSet) throws SQLException
  {
    int ticketId = resultSet.getInt("ticket_id");
    int scheduleId = resultSet.getInt("schedule_id");
    String email = resultSet.getString("user_email");
    int trainId = resultSet.getInt("train_id");

    // Create a proper Schedule object with all required information
    String departureStationName = resultSet.getString("departureStation");
    String arrivalStationName = resultSet.getString("arrivalStation");

    // Create stations
    Station departureStation = new Station(departureStationName);
    Station arrivalStation = new Station(arrivalStationName);

    // Create dates with both date and time components
    java.sql.Date depDate = resultSet.getDate("departureDate");
    java.sql.Time depTime = resultSet.getTime("departureTime");
    java.sql.Date arrDate = resultSet.getDate("arrivalDate");
    java.sql.Time arrTime = resultSet.getTime("arrivalTime");

    // Convert to application's MyDate objects
    MyDate departureDate = convertToMyDate(depDate, depTime);
    MyDate arrivalDate = convertToMyDate(arrDate, arrTime);

    Schedule schedule = new Schedule(scheduleId, departureStation, arrivalStation, departureDate, arrivalDate);

    // Create train and set its schedule
    Train train = new Train(trainId);
    train.setSchedule(schedule);

    // Create basic ticket
    Ticket ticket = new Ticket(ticketId, train, schedule, email);

    // Add seat if present
    int seatNumber = resultSet.getInt("seat_number");
    if (!resultSet.wasNull())
    {
      ticket.setSeatId(new Seat(seatNumber));
    }

    // Add bicycle if present
    int bicycleNumber = resultSet.getInt("bicycle_number");
    if (!resultSet.wasNull())
    {
      ticket.setBicycleSeat(new Bicycle(bicycleNumber));
    }

    return ticket;
  }

  private MyDate convertToMyDate(java.sql.Date date, java.sql.Time time)
  {
    if (date == null)
    {
      return null;
    }

    java.util.Calendar cal = java.util.Calendar.getInstance();

    // Set date components
    cal.setTime(date);
    int year = cal.get(java.util.Calendar.YEAR);
    int month = cal.get(java.util.Calendar.MONTH) + 1; // Calendar months are 0-based
    int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

    // Set time components if time is available
    int hour = 0;
    int minute = 0;
    if (time != null)
    {
      java.util.Calendar timeCal = java.util.Calendar.getInstance();
      timeCal.setTime(time);
      hour = timeCal.get(java.util.Calendar.HOUR_OF_DAY);
      minute = timeCal.get(java.util.Calendar.MINUTE);
    }

    // Create MyDate object with correct parameter order: day, month, year, hour, minute
    return new MyDate(day, month, year, hour, minute);
  }
}