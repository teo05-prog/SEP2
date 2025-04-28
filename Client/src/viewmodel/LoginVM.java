package viewmodel;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import model.services.AuthenticationService;

public class LogInVM
{
  private final Property<String> email = new SimpleStringProperty();
  private final Property<String> password = new SimpleStringProperty();
  private final StringProperty message = new SimpleStringProperty();
  private final Property<Boolean> isAdmin = new SimpleBooleanProperty(false);
  private final BooleanProperty disableLoginButton = new SimpleBooleanProperty(true);

  private final AuthenticationService authService;
  private final BooleanProperty loginSucceeded = new SimpleBooleanProperty(false);

  public LogInVM(AuthenticationService authService)
  {
    this.authService = authService;
    email.addListener(
        (observable, oldValue, newValue) -> validate());
    password.addListener(
        (observable, oldValue, newValue) -> validate());
    isAdmin.addListener(
        (observable, oldValue, newValue) -> validate());

  }
  public void validate(){}
  public Property<String> emailProperty()
  {
  }

  public Property<String> passwordProperty()
  {
  }

  public Property<Boolean> isAdminProperty()
  {
  }

  public ObservableValue<String> messageProperty()
  {
  }

  public ObservableValue<Boolean> enableLoginButtonProperty()
  {
  }

  public BooleanProperty loginSucceededProperty()
  {
    return loginSucceeded;
  }

  public void loginUser()
  {
  }
}
