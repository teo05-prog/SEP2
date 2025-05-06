package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AdminMyAccountVM
{
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();

  public AdminMyAccountVM()
  {
    // to be replaced with actual data loading
    loadUserData();
  }

  private void loadUserData()
  {
    // to be replaced with actual data loading
    name.set("John Johnson");
    email.set("john@example.com");
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
