package viewmodel;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import model.services.AuthenticationService;
import model.services.LoginRequest;
import view.ViewHandler;

import java.util.ArrayList;
import java.util.List;

public class LoginVM
{
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty password = new SimpleStringProperty();
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty isAdmin = new SimpleBooleanProperty(false);
  private final BooleanProperty disableLoginButton = new SimpleBooleanProperty(
      true);

  private final AuthenticationService authService;
  private final BooleanProperty loginSucceeded = new SimpleBooleanProperty(
      false);

  public LoginVM(AuthenticationService authService)
  {
    this.authService = authService;
    email.addListener((observable, oldValue, newValue) -> validate());
    password.addListener((observable, oldValue, newValue) -> validate());
    isAdmin.addListener((observable, oldValue, newValue) -> validate());

  }

  public void validate()
  {
    List<String> errors = new ArrayList<>();
    // email validation
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
    // password validation
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
    // final validation result
    disableLoginButton.set(!errors.isEmpty());
    message.set(String.join("\n", errors));
  }

  public StringProperty emailProperty()
  {
    return email;
  }

  public StringProperty passwordProperty()
  {
    return password;
  }

  public BooleanProperty isAdminProperty()
  {
    return isAdmin;
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

  public void loginUser()
  {
    if (!disableLoginButton.get())
    {
      // Create a login request with the user credentials
      LoginRequest request = new LoginRequest(email.get(), password.get());

      // Call the authentication service to validate credentials
      String result = authService.login(request);


      if ("Ok".equals(result))
      {
        message.set("Login successful");
        loginSucceeded.set(true);
        email.set("");
        password.set("");
      }
      else if ("Email not found.".equals(result))
      {
        message.set("Login failed: " + result);
        loginSucceeded.set(false);
      }
      else if ("Incorrect password.".equals(result))
      {
        message.set("Login failed: " + result);
        loginSucceeded.set(false);
      }
      else if ("User is not admin.".equals(result))
      {
        message.set("Login failed: " + result);
        loginSucceeded.set(false);
      }
      else if ("User is admin.".equals(result))
      {
        message.set("Login successful");
        loginSucceeded.set(true);
        email.set("");
        password.set("");
      }
      else
      {
        message.set("Login failed: " + result);
        loginSucceeded.set(false);
      }

    }
  }
}