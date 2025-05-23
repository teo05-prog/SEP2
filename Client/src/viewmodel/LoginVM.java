package viewmodel;

import dtos.LoginDTO;
import dtos.Request;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import network.ClientSocket;
import session.Session;
import view.ViewHandler;

public class LoginVM
{
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty password = new SimpleStringProperty();
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableLoginButton = new SimpleBooleanProperty(true);
  private final BooleanProperty loginSucceeded = new SimpleBooleanProperty(false);

  public LoginVM()
  {
    disableLoginButton.bind(email.isEmpty().or(password.isEmpty()));
  }

  public void loginUser()
  {
    if (!disableLoginButton.get())
    {
      LoginDTO loginRequest = new LoginDTO(email.get(), password.get());
      Request request = new Request("login", "login", loginRequest);
      try
      {
        //send login request
        Object response = ClientSocket.sentRequest(request);
        //expecting the server to return the user's email
        String userEmail = (String) response;
        //store it in the session
        Session.getInstance().setUserEmail(userEmail);

        //ask server for role
        Request roleRequest = new Request("login", "getRole", userEmail);
        Object roleResponse = ClientSocket.sentRequest(roleRequest);
        String role = (String) roleResponse;

        Session.getInstance().setIsAdmin("ADMIN".equals(role));

        message.set(response.toString());
        loginSucceeded.set(true);

        //route user
        if (Session.getInstance().isAdmin())
        {
          ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
        }
        else
        {
          ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
        }

        email.set("");
        password.set("");
      }
      catch (RuntimeException e)
      {
        handleError("Login error: " + e.getMessage());
      }
    }
  }

  private void handleError(String errorMessage)
  {
    System.out.println(errorMessage);
    loginSucceeded.set(false);
    message.set(errorMessage);
  }

  // Property getters
  public StringProperty emailProperty()
  {
    return email;
  }

  public StringProperty passwordProperty()
  {
    return password;
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public BooleanProperty enableLoginButtonProperty()
  {
    return disableLoginButton;
  }

  public BooleanProperty loginSucceededProperty()
  {
    return loginSucceeded;
  }

}
