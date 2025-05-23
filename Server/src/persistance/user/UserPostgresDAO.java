package persistance.user;

import model.entities.Admin;
import model.entities.MyDate;
import model.entities.Traveller;
import model.entities.User;

import java.sql.*;

public class UserPostgresDAO implements UserDAO
{
  private static UserPostgresDAO instance;

  public UserPostgresDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized UserPostgresDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new UserPostgresDAO();
    }
    return instance;
  }

  private static Connection getConnection() throws SQLException
  {
    //return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres", "14012004");
    return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=viarail", "postgres",
        "141220");
  }

  @Override public void createTraveller(String name, String email, String password, MyDate birthDate)
  {
    try (Connection connection = getConnection())
    {
      String sql = "INSERT INTO users (name, email, password, isAdmin, birthday) VALUES (?, ?, ?, false, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, name);
      statement.setString(2, email);
      statement.setString(3, password);
      statement.setDate(4, birthDate.toSqlDate());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @Override public User readByEmail(String email)
  {
    try (Connection connection = getConnection())
    {
      String sql = "SELECT * FROM users WHERE email = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, email);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next())
      {
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("isAdmin");

        if (isAdmin)
        {
          Admin admin = new Admin(name, email, password);
          return admin;
        }
        else
        {
          Date sqlDate = resultSet.getDate("birthday");
          MyDate birthDate = sqlDate != null ? new MyDate(sqlDate) : null;
          return new Traveller(name, email, password, birthDate);
        }
      }
    }
    catch (SQLException e)
    {
      System.out.println("Database error: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  @Override public void deleteUser(String email)
  {
    try (Connection connection = getConnection())
    {
      String sql = "DELETE FROM users WHERE email = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, email);
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
