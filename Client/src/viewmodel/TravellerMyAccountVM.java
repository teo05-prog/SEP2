package viewmodel;

import dtos.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.Traveller;
import model.entities.User;
import network.ClientSocket;
import session.Session;

import java.util.Map;

public class TravellerMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty("Loading...");
  private final StringProperty birthday = new SimpleStringProperty("Loading...");
  private final StringProperty email = new SimpleStringProperty("Loading...");

  public TravellerMyAccountVM()
  {
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
        Request userRequest = new Request("user", "getUserDetails", userEmail);
        Object response = ClientSocket.sentRequest(userRequest);
        if (response != null)
        {
          if (response instanceof Map<?, ?>)
          {
            Map<String, Object> userDetails = (Map<String, Object>) response;

            String userName = (String) userDetails.get("name");
            String userBirthday = (String) userDetails.get("birthday");

            name.set(userName != null ? userName : "N/A");
            birthday.set(userBirthday != null ? userBirthday : "N/A");

            System.out.println("Loaded user data: " + userName + ", " + userEmail + ", " + userBirthday);
          }
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
          else
          {
            name.set("Data format error");
            birthday.set("Data format error");
            System.out.println("Unexpected response format: " + response.getClass().getName());
          }
        }
        else
        {
          name.set("User data not found");
          birthday.set("User data not found");
          System.out.println("No user details returned from server");
        }
      }
      catch (Exception e)
      {
        System.out.println("Error loading user data: " + e.getMessage());
        e.printStackTrace();
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
