package viewmodel;

import dtos.AuthenticationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.User;
import view.ViewHandler;

public class AdminMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();
  private final AuthenticationService authService;

  public AdminMyAccountVM()
  {
    this.authService = ViewHandler.getAuthService();
    loadUserData();
  }

  private void loadUserData()
  {
    User currentUser = authService.getCurrentUser();
    if (currentUser != null)
    {
      name.set(currentUser.getName());
      email.set(currentUser.getEmail());
    }
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
