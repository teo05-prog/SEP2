package viewmodel;

import dtos.SearchFilterDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import model.MyDate;
import network.ClientSocket;
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

  private final String userEmail;

  private final BooleanBinding inputValid;

  public SearchTicketVM(String userEmail)
  {
    this.userEmail = userEmail;
    inputValid = from.isNotEmpty().and(to.isNotEmpty()).and(date.isNotNull())
        .and(time.isNotEmpty());
  }

  public SearchTicketVM() {
    this.userEmail = null;
    inputValid = from.isNotEmpty().and(to.isNotEmpty()).and(date.isNotNull()).and(time.isNotEmpty());
  }

  public String getUserEmail(){
    return userEmail;
  }

  public void startTrainSearch()
  {
    //create and send a request to the server
    LocalDate localDate = date.get();
    String [] split = time.get().split(":");
    int hour = Integer.parseInt(split[0]);
    int minute = Integer.parseInt(split[1]);
    MyDate myDate = new MyDate(localDate.getDayOfMonth(),
        localDate.getMonthValue(), localDate.getYear(), hour, minute);

    String email = getUserEmail(); // from ViewModel
    SearchFilterDTO filterDTO = new SearchFilterDTO(email, from.get(), to.get(),
        myDate, seat.get(), bicycle.get());

    ClientSocket.sendRequest("search","storeFilter",filterDTO);
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
