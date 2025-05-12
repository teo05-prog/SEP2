package viewmodel;

import javafx.beans.property.*;
import dtos.AuthenticationService;
import dtos.LoginRequest;
import services.AuthenticationServiceImpl;
import view.ViewHandler;
import persistance.user.UserDAO;
import persistance.user.UserPostgresDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginVM
{
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty password = new SimpleStringProperty();
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableLoginButton = new SimpleBooleanProperty(true);
  private final BooleanProperty loginSucceeded = new SimpleBooleanProperty(false);
  private final StringProperty currentUserEmail = new SimpleStringProperty();
  private final AuthenticationService authService;

  public LoginVM() throws SQLException
  {
    UserDAO userDAO = new UserPostgresDAO();
    this.authService = new AuthenticationServiceImpl(userDAO, null);

    ViewHandler.setAuthService(authService);

    email.addListener((observable, oldValue, newValue) -> validate());
    password.addListener((observable, oldValue, newValue) -> validate());
    currentUserEmail.set(null);
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

      if (atPos <= 0 || dotPos <= atPos + 1 || dotPos == emailValue.length() - 1)
      {
        errors.add("Email must contain '@' and a domain (e.g. 'user@domain.com').");
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
      try
      {
        LoginRequest loginRequest = new LoginRequest(email.get(), password.get());
        String response = authService.login(loginRequest);

        if ("Ok".equals(response))
        {
          currentUserEmail.set(email.get());
          loginSucceeded.set(true);
          message.set("Login successful");

          if (authService.isCurrentUserAdmin())
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
        else
        {
          handleError("Login failed: " + response);
        }
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
    currentUserEmail.set(null);
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

  public String getCurrentUserEmail()
  {
    return currentUserEmail.get();
  }

  public boolean isUserAdmin()
  {
    return authService.isCurrentUserAdmin();
  }
}