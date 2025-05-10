package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Train;
import view.ViewHandler;

public class ChooseTrainVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final ObservableList<Train> trainList = FXCollections.observableArrayList();
  private final ObjectProperty<Train> selectedTrain = new SimpleObjectProperty<>();

  public ChooseTrainVM()
  {
    //will call the service here
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public ObservableList<Train> getTrainList()
  {
    return trainList;
  }

  public ObjectProperty<Train> selectedTrainProperty()
  {
    return selectedTrain;
  }

  public void continueWithSelectedTrain()
  {
    // later we will have a database
    if (selectedTrain.get() == null)
    {
      message.set("Please select a train before continuing.");
    }
  }
}
