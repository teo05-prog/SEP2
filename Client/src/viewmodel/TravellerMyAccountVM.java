package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import session.Session;

public class TravellerMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty birthday = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();

  public TravellerMyAccountVM()
  {
    loadUserData();
  }

  private void loadUserData()
  {
    String userEmail = Session.getInstance().getUserEmail();
    String userName = Session.getInstance().getUserName();
    String birthday = Session.getInstance().getBirthday();

    if (userEmail != null)
    {
      email.set(userEmail);
      name.set(userName != null ? userName : "N/A");
      this.birthday.set(birthday != null ? birthday : "N/A");
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
