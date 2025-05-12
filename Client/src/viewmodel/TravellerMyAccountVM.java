package viewmodel;

import dtos.AuthenticationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.Traveller;
import model.entities.User;
import view.ViewHandler;

public class TravellerMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty birthday = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();
  private final AuthenticationService authService;

  public TravellerMyAccountVM()
  {
    this.authService = ViewHandler.getAuthService();
    loadUserData();
  }

  private void loadUserData()
  {
    User currentUser = authService.getCurrentUser();
    System.out.println("Loading user data. AuthService " + authService);
    System.out.println("Current user: " + currentUser);
    if (currentUser != null && currentUser instanceof Traveller) {
      Traveller traveller = (Traveller) currentUser;
      name.set(traveller.getName());
      birthday.set(traveller.getBirthDate().toString());
      email.set(traveller.getEmail());
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
