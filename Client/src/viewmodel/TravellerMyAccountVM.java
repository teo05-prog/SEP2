package viewmodel;

import dtos.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.Traveller;
import model.entities.User;
import network.ClientSocket;
import persistance.user.UserDAO;
import persistance.user.UserPostgresDAO;
import session.Session;

import java.sql.SQLException;

public class TravellerMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty("Loading...");
  private final StringProperty birthday = new SimpleStringProperty("Loading...");
  private final StringProperty email = new SimpleStringProperty("Loading...");

  private UserDAO userDAO;

  public TravellerMyAccountVM()
  {
    try
    {
      this.userDAO = UserPostgresDAO.getInstance();
    }
    catch (SQLException e)
    {
      System.out.println("Error initializing UserDAO: " + e.getMessage());
      e.printStackTrace();
    }
    loadUserData();
  }

  private void loadUserData()
  {
    // Get user email from Session
    String userEmail = Session.getInstance().getUserEmail();

    if (userEmail != null && !userEmail.isEmpty())
    {
      // Set the email since we know it
      email.set(userEmail);

      try
      {
        // Create a request to get user details from the server
        Request userRequest = new Request("user", "getUserDetails", userEmail);
        Object response = ClientSocket.sentRequest(userRequest);

        if (response != null)
        {
          // Parse the user details from the response
          // This will depend on what format your server returns
          // For example, if it returns a map:
          if (response instanceof java.util.Map<?, ?>)
          {
            java.util.Map<String, Object> userDetails = (java.util.Map<String, Object>) response;

            // Extract user details
            String userName = (String) userDetails.get("name");
            String userBirthday = (String) userDetails.get("birthday");

            // Update the properties
            name.set(userName != null ? userName : "N/A");
            birthday.set(userBirthday != null ? userBirthday : "N/A");

            System.out.println("Loaded user data: " + userName + ", " + userEmail + ", " + userBirthday);
          }
          // If your server returns a custom User object
          else if (response instanceof User)
          {
            User user = (User) response;
            name.set(user.getName() != null ? user.getName() : "N/A");

            if (user instanceof Traveller)
            {
              Traveller traveller = (Traveller) user;
              if (traveller.getBirthDate() != null)
              {
                birthday.set(traveller.getBirthDate().toString());
              }
              else
              {
                birthday.set("N/A");
              }
            }
            else
            {
              birthday.set("N/A");
            }
          }
          // If your server returns a serialized string or other format
          else
          {
            // Handle according to your application's protocol
            name.set("Data format error");
            birthday.set("Data format error");
            System.out.println("Unexpected response format: " + response.getClass().getName());
          }
        }
        else
        {
          // No user details returned
          name.set("User data not found");
          birthday.set("User data not found");
          System.out.println("No user details returned from server");
        }
      }
      catch (Exception e)
      {
        System.out.println("Error loading user data: " + e.getMessage());
        e.printStackTrace();

        // Set default values in case of error
        name.set("Error loading data");
        birthday.set("Error loading data");
      }
    }
  }

  public void refresh()
  {
    loadUserData();
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public StringProperty birthdayProperty()
  {
    return birthday;
  }

  public StringProperty emailProperty()
  {
    return email;
  }
}
