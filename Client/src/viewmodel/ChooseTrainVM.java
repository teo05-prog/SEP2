package viewmodel;

import com.google.gson.Gson;
import dtos.TrainDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Train;
import network.ClientSocket;
import session.Session;

public class ChooseTrainVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final ObservableList<TrainDTO> trainList = FXCollections.observableArrayList();
  private final ObjectProperty<TrainDTO> selectedTrain = new SimpleObjectProperty<>();

  public ChooseTrainVM()
  {
    //will call the service here
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public ObservableList<TrainDTO> getTrainList()
  {
    return trainList;
  }

  public ObjectProperty<TrainDTO> selectedTrainProperty()
  {
    return selectedTrain;
  }

  public void continueWithSelectedTrain()
  {
    if (selectedTrain.get() == null)
    {
      message.set("Please select a train before continuing.");
    }
    else {
      //save selected train in session
      Session.getInstance().setSelectedTrainDTO(selectedTrain.get());
    }
  }

  public void loadFilteredTrains(){
    try{
      //get user email from the session
      String email = Session.getInstance().getUserEmail();
      Object response = ClientSocket.sendRequest("search", "getFilteredTrains", email);
      TrainDTO[] trains = new Gson().fromJson(response.toString(), TrainDTO[].class);

      trainList.setAll(trains); //update observable list
    }
    catch (Exception e)
    {
      message.set("Failed to load trains: "+e.getMessage());
    }
  }
}
