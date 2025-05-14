package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.User;
import session.Session;

public class AdminMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();

  public AdminMyAccountVM()
  {
    loadUserData();
  }

  private void loadUserData()
  {
    String userEmail = Session.getInstance().getUserEmail();
    String userName = Session.getInstance().getUserName(); // Optional

    if (userEmail != null) {
      email.set(userEmail);
      name.set(userName != null ? userName : "Admin");
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
