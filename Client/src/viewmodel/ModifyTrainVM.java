package viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Train;

public class ModifyTrainVM
{
  private final StringProperty trainID = new SimpleStringProperty();
  private final BooleanProperty saveButtonDisabled = new SimpleBooleanProperty(true);
  private final ObservableList<Train> trainData = FXCollections.observableArrayList();

  public ModifyTrainVM()
  {
    saveButtonDisabled.bind(trainID.isEmpty());
  }

  public StringProperty getTrainIDProperty()
  {
    return trainID;
  }

  public BooleanProperty getSaveButtonDisabledProperty()
  {
    return saveButtonDisabled;
  }

  public ObservableList<Train> getTrainData()
  {
    return trainData;
  }

  public void loadTrainData(Train train)
  {
    //trainID.set(train.getTrainID()); to be implemented
    trainData.clear();
    trainData.add(train);
  }

  public void saveChanges()
  {
    System.out.println("Saving changes for train: " + trainID.get());
  }

}
