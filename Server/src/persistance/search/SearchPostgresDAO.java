package persistance.search;

import dtos.SearchFilterDTO;
import dtos.TrainDTO;
import model.MyDate;
import utilities.LogLevel;
import utilities.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SearchPostgresDAO implements SearchDAO{

  private static SearchPostgresDAO instance;
  private final Logger logger;

  private SearchPostgresDAO(Logger logger){
    this.logger = logger;
  }

  public static synchronized void init(Logger logger){
    if (instance == null){
      instance = new SearchPostgresDAO(logger);
    }
  }

  public static synchronized SearchPostgresDAO getInstance(){
    if (instance == null){
      throw new IllegalStateException("SearchPostgresDAO not initialized. Call init() first");
    }
    return instance;
  }

  private Connection getConnection() throws SQLException{
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail",
        "postgres", "14012004");
  }

  @Override
  public void saveFilter(SearchFilterDTO filterDTO)
  {
    String sql = """
     INSERT INTO search_filter
     (user_email, from_station, to_station, departureDate, departureTime, seat_needed, bicycle_needed)
     VALUES (?, ?, ?, ?, ?, ?, ?)
     """;
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setString(1, filterDTO.userEmail);
      statement.setString(2, filterDTO.from);
      statement.setString(3, filterDTO.to);
      //convert MyDate to SQL Date and Time
      statement.setDate(4, filterDTO.myDate.toSqlDate());
      statement.setTime(5,filterDTO.myDate.toSqlTime());
      statement.setBoolean(6, filterDTO.seat);
      statement.setBoolean(7, filterDTO.bicycle);

      statement.executeUpdate();
      logger.log("Search filter saved for user: "+ filterDTO.userEmail, LogLevel.INFO);
    }catch (SQLException e){
      logger.log("Error saving search filter: "+e.getMessage(), LogLevel.ERROR);
    }
  }

  @Override
  public List<TrainDTO> getFilteredTrainsForUser(String email){
    List<TrainDTO> trains = new ArrayList<>();

    String sql = """
        SELECT t.train_id, s.departureStation, s.arrivalStation, s.departureDate, s.departureTime
        FROM schedule s
        JOIN train t ON t.train_id = s.train_id
        WHERE s.departureStation =(
        SELECT from_station FROM search_filter WHERE user_email = ? ORDER BY created_at DESC LIMIT 1
        )
        AND s.arrivalStation =(
        SELECT to_station FROM search_filter WHERE user_email = ? ORDER BY created_at DESC LIMIT 1
        )
        AND s.departureDate =(
        SELECT departureDate FROM search_filter WHERE user_email = ? ORDER BY created_at DESC LIMIT 1
        );
        """;
    try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

      statement.setString(1,email);
      statement.setString(2,email);
      statement.setString(3,email);

      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        int id = resultSet.getInt("train_id");
        String from = resultSet.getString("departureStation");
        String to = resultSet.getString("arrivalStation");

        LocalDate date = resultSet.getDate("departureDate").toLocalDate();
        LocalTime time = resultSet.getTime("departureDate").toLocalTime();

        MyDate myDate = new MyDate(
            date.getDayOfMonth(),
            date.getMonthValue(),
            date.getYear(),
            time.getHour(),
            time.getMinute()
        );

        TrainDTO trainDTO = new TrainDTO(id, from, to, myDate);
        trains.add(trainDTO);
      }
    }catch (SQLException e){
      logger.log("SQL error loading trains: "+e.getMessage(),LogLevel.ERROR);
    }
    return trains;
  }
}
