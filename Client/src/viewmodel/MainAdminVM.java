package viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainAdminVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final BooleanProperty disableRemoveButton = new SimpleBooleanProperty(true);
  private final BooleanProperty disableModifyButton = new SimpleBooleanProperty(true);
  private final BooleanProperty trainSelected = new SimpleBooleanProperty(false);

  public MainAdminVM()
  {
    disableRemoveButton.bind(trainSelected.not());
    disableModifyButton.bind(trainSelected.not());
  }

  public BooleanProperty trainSelectedProperty()
  {
    return trainSelected;
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public BooleanProperty enableRemoveButtonProperty()
  {
    return disableRemoveButton;
  }

  public BooleanProperty enableModifyButtonProperty()
  {
    return disableModifyButton;
  }
}
