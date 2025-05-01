package viewmodel;

import javafx.beans.property.*;
import model.entities.MyDate;
import model.entities.Traveller;
import model.entities.User;
import model.services.AuthenticationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterVM
{

  private final StringProperty name = new SimpleStringProperty();
  private final ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();
  private final StringProperty email = new SimpleStringProperty();
  private final StringProperty password = new SimpleStringProperty();
  private final StringProperty repeatPassword = new SimpleStringProperty();
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableRegisterButton = new SimpleBooleanProperty(true);

  private final AuthenticationService authService;
  private final BooleanProperty registrationSucceeded = new SimpleBooleanProperty(false);

  public RegisterVM(AuthenticationService authService)
  {
    this.authService = authService;

    name.addListener(((observable, oldValue, newValue) -> validate()));
    birthDate.addListener((observable, oldValue, newValue) -> validate());
    email.addListener((observable, oldValue, newValue) -> validate());
    password.addListener((observable, oldValue, newValue) -> validate());
    repeatPassword.addListener((observable, oldValue, newValue) -> validate());
  }

  private void validate()
  {
    List<String> errors = new ArrayList<>();

    // name validation
    if (name.get() == null || name.get().trim().isEmpty())
    {
      errors.add("Name cannot be empty.");
    }
    else
    {
      if (!Character.isLetter(name.get().charAt(0)))
      {
        errors.add("Name must start with a letter.");
      }
      if (name.get().length() > 70)
      {
        errors.add("Name cannot be longer than 70 characters.");
      }
    }
    // birthDate validation
    if (birthDate.get() == null)
    {
      errors.add("Birthday is required");
    }
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

      if (atPos <= 0 || dotPos <= atPos + 1 || dotPos == emailValue.length() - 1)
      {
        errors.add("Email must contain '@' and a domain (e.g. 'user@domain.com').");
      }
    }
    //password validation
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
    // repeat password validation
    if (repeatPassword.get() == null || !repeatPassword.get().equals(password.get()))
    {
      errors.add("Repeat password must match the password.");
    }
    // final validation result
    disableRegisterButton.set(!errors.isEmpty());
    message.set(String.join("\n", errors));
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public ObjectProperty<LocalDate> birthDateProperty()
  {
    return birthDate;
  }

  public StringProperty emailProperty()
  {
    return email;
  }

  public StringProperty passwordProperty()
  {
    return password;
  }

  public StringProperty repeatPasswordProperty()
  {
    return repeatPassword;
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public BooleanProperty enableRegisterButtonProperty()
  {
    return disableRegisterButton;
  }

  public BooleanProperty registrationSucceededProperty()
  {
    return registrationSucceeded;
  }

  public void registerUser()
  {
    if (!disableRegisterButton.get())
    {
      LocalDate localDate = birthDate.get();
      MyDate myDate = new MyDate(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());

      Traveller traveller = new Traveller(name.get(), password.get(), email.get(), myDate);
      String result = authService.register(traveller);

      if ("success".equalsIgnoreCase(result))
      {
        message.set("Successfully registered!");
        registrationSucceeded.set(true);
      }
      else
      {
        message.set("Registration failed: Email might already be in use.");
        registrationSucceeded.set(false);
      }
    }
  }
}
