package viewmodel;

import javafx.beans.property.Property;
import model.services.AuthenticationService;

import java.time.LocalDate;

public class RegisterVM
{
  public RegisterVM(AuthenticationService authService)
  {
  }

  public Property<String> nameProperty()
  {
    return null;
  }

  public Property<LocalDate> birthDateProperty()
  {
    return null;
  }

  public Property<String> emailProperty()
  {
    return null;
  }

  public Property<String> passwordProperty()
  {
    return null;
  }

  public Property<String> repeatPasswordProperty()
  {
    return null;
  }

  public Property<String> messageProperty()
  {
    return null;
  }

  public Property<Boolean> enableRegisterButtonProperty()
  {
    return null;
  }

  public void registerUser()
  {
    // Registration logic goes here
  }
}
