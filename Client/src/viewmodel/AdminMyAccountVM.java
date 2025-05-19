package viewmodel;

import dtos.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.Admin;
import model.entities.User;
import network.ClientSocket;
import persistance.user.UserDAO;
import persistance.user.UserPostgresDAO;
import session.Session;

import java.sql.SQLOutput;

public class AdminMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty("Loading...");
  private final StringProperty email = new SimpleStringProperty("Loading...");

  private UserDAO userDAO;

  public AdminMyAccountVM()
  {
    try
    {
      this.userDAO = UserPostgresDAO.getInstance();
    }
    catch (Exception e)
    {
      System.out.println("Error initializing UserDAO: " + e.getMessage());
      e.printStackTrace();
    }
    loadUserData();
  }

  private void loadUserData()
  {
    String userEmail = Session.getInstance().getUserEmail();

    if (userEmail != null && !userEmail.isEmpty())
    {
      email.set(userEmail);
      try
      {
        // Create a request to get user details from the server
        Request userRequest = new Request("user", "getUserDetails", userEmail);
        Object response = ClientSocket.sentRequest(userRequest);

        if (response != null)
        {
          // Parse the user details from the response
          if (response instanceof java.util.Map<?, ?>)
          {
            java.util.Map<String, Object> userDetails = (java.util.Map<String, Object>) response;

            // Extract user details
            String userName = (String) userDetails.get("name");
            name.set(userName != null ? userName : "Admin");

            System.out.println("User details loaded: " + userName + ", " + userEmail);
          }
          else
          {
            System.out.println("Unexpected response type: " + response.getClass().getName());
          }
        }
        else
        {
          name.set("User data not found");
          System.out.println("No user details returned from server");
        }
      }
      catch (Exception e)
      {
        System.out.println("Error loading user data: " + e.getMessage());
        e.printStackTrace();

        name.set("Error loading user data");
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

  public StringProperty emailProperty()
  {
    return email;
  }
}
