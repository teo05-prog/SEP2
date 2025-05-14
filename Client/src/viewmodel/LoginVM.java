package viewmodel;
import dtos.LoginRequest;
import dtos.Request;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import network.ClientSocket;
import session.Session;
import view.ViewHandler;

import java.util.ArrayList;
import java.util.List;

public class LoginVM
{
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty password = new SimpleStringProperty();
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableLoginButton = new SimpleBooleanProperty(
      true);
  private final BooleanProperty loginSucceeded = new SimpleBooleanProperty(
      false);

  public LoginVM()
  {
    disableLoginButton.bind(email.isEmpty().or(password.isEmpty()));
  }

  public void validate()
  {
    List<String> errors = new ArrayList<>();

    // Email validation
    String emailValue = email.get();
    if (emailValue == null || emailValue.trim().isEmpty())
    {
      errors.add("Email cannot be empty.");
    }
    else
    {
      int atPos = emailValue.indexOf('@');
      int dotPos = emailValue.lastIndexOf('.');

      if (atPos <= 0 || dotPos <= atPos + 1
          || dotPos == emailValue.length() - 1)
      {
        errors.add(
            "Email must contain '@' and a domain (e.g. 'user@domain.com').");
      }
    }

    // Password validation
    String pwd = password.get();
    if (pwd == null)
    {
      errors.add("Password is required.");
    }
    else
    {
      if (pwd.length() < 8 || pwd.length() > 25)
      {
        errors.add("Password must be 8–25 characters long.");
      }
      else
      {
        boolean hasNumber = false;
        boolean hasSymbol = false;

        for (char c : pwd.toCharArray())
        {
          if (Character.isDigit(c))
            hasNumber = true;
          else if (!Character.isLetter(c))
            hasSymbol = true;
        }
        if (!hasNumber)
        {
          errors.add("Password must contain at least one number.");
        }
        if (!hasSymbol)
        {
          errors.add("Password must contain at least one symbol.");
        }
      }
    }

    disableLoginButton.set(!errors.isEmpty());
    message.set(String.join("\n", errors));
  }

  public void loginUser()
  {
    if (!disableLoginButton.get())
    {
      LoginRequest loginRequest = new LoginRequest(email.get(), password.get());
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
        Request roleRequest = new Request("login","getRole",userEmail);
        Object roleResponse = ClientSocket.sentRequest(roleRequest);
        String role = (String) roleResponse;

        Session.getInstance().setIsAdmin("ADMIN".equals(role));

        message.set(response.toString());
        loginSucceeded.set(true);

        //route user
        if (Session.getInstance().isAdmin())
        {
          System.out.println("User is admin, redirecting to admin view");
          ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
        }
        else
        {
          System.out.println("User is not admin, redirecting to user view");
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
