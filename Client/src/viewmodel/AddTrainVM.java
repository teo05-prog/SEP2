package viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;

public class AddTrainVM
{
  private final StringProperty message = new SimpleStringProperty();
  private final StringProperty trainID = new SimpleStringProperty();
  private final StringProperty departureStations = new SimpleStringProperty();
  private final StringProperty arrivalStations = new SimpleStringProperty();
  private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
  private final ObjectProperty<LocalDate> arrivalDate = new SimpleObjectProperty<>();
  private final BooleanProperty addButtonDisabled = new SimpleBooleanProperty(true);
  private final BooleanProperty addTrainSuccess = new SimpleBooleanProperty(false);

  public AddTrainVM()
  {
    addButtonDisabled.bind(trainID.isEmpty()
        .or(departureStations.isEmpty().or(arrivalStations.isEmpty()).or(departureDate.isNull())
            .or(arrivalDate.isNull())));
  }

  public StringProperty messageProperty()
  {
    return message;
  }

  public StringProperty trainIDProperty()
  {
    return trainID;
  }

  public ObjectProperty<LocalDate> arrivalDateProperty()
  {
    return arrivalDate;
  }

  public ObjectProperty<LocalDate> departureDateProperty()
  {
    return departureDate;
  }

  public ObservableValue<Boolean> getAddTrainButtonDisabledProperty()
  {
    return addButtonDisabled;
  }

  public boolean isAddTrainSuccess()
  {
    return addTrainSuccess.get();
  }

  public void addTrain()
  {
    if (departureStations.get() == null || departureStations.get().isEmpty())
    {
      message.set("Departure station cannot be empty.");
      return;
    }
    if (arrivalStations.get() == null || arrivalStations.get().isEmpty())
    {
      message.set("Arrival station cannot be empty.");
      return;
    }
    if (departureDate.get() == null)
    {
      message.set("Departure date cannot be empty.");
      return;
    }
    if (arrivalDate.get() == null)
    {
      message.set("Arrival date cannot be empty.");
      return;
    }
    // add Train to the database, to be implemented
    addTrainSuccess.set(true);
  }
}
