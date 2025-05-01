package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TravellerMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty birthday = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();

  public TravellerMyAccountVM()
  {
    // to be replaced with actual data loading
    loadUserData();
  }

  private void loadUserData()
  {
    // to be replaced with actual data loading
    name.set("John Doe");
    birthday.set("01/01/1990");
    email.set("john@example.com");
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
