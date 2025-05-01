package viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entities.Train;
import model.entities.TrainList;

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

  public void removeTrain(Train selectedItem)
  {
    if (selectedItem == null)
    {
      message.set("No train selected.");
      return;
    }
    TrainList trainList = new TrainList();
    trainList.removeTrain(selectedItem);
    message.set("Train " + selectedItem.getTrainId() + " removed.");
    trainSelected.set(false);
  }

  public void updateTrainsList()
  {
    // to be implemented
  }
}
