package viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import view.ViewHandler;

import java.time.LocalDate;

public class SearchTicketVM
{
  private final StringProperty from = new SimpleStringProperty();
  private final StringProperty to = new SimpleStringProperty();
  private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
  private final StringProperty time = new SimpleStringProperty();
  private final BooleanProperty seat = new SimpleBooleanProperty();
  private final BooleanProperty bicycle = new SimpleBooleanProperty();

  private final BooleanBinding inputValid;

  public SearchTicketVM()
  {
    inputValid = from.isNotEmpty().and(to.isNotEmpty()).and(date.isNotNull()).and(time.isNotEmpty());
  }

  public void startTrainSearch()
  {
    //navigate to the next page
    ViewHandler.showView(ViewHandler.ViewType.CHOOSE_TRAIN);
  }

  public boolean isSeatOrBicycleSelected()
  {
    return seat.get() || bicycle.get();
  }

  public BooleanBinding inputValidProperty()
  {
    return inputValid;
  }

  public StringProperty fromProperty()
  {
    return from;
  }

  public StringProperty toProperty()
  {
    return to;
  }

  public ObjectProperty<LocalDate> dateProperty()
  {
    return date;
  }

  public StringProperty timeProperty()
  {
    return time;
  }

  public BooleanProperty seatProperty()
  {
    return seat;
  }

  public BooleanProperty bicycleProperty()
  {
    return bicycle;
  }
}
