package viewmodel;

import dtos.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import network.ClientSocket;
import session.Session;

import java.util.Map;

public class AdminMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty("Loading...");
  private final StringProperty email = new SimpleStringProperty("Loading...");

  public AdminMyAccountVM()
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
            name.set(userName != null ? userName : "Admin");
          }
        }
        else
        {
          name.set("User data not found");
        }
      }
      catch (Exception e)
      {
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
