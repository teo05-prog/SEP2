package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.HashSet;
import java.util.Set;

public class SeatSelectionVM
{
  private final ObservableSet<Integer> selectedSeats = FXCollections.observableSet(new HashSet<>());
  private final Set<Integer> bookedSeats = new HashSet<>();

  public ObservableSet<Integer> getSelectedSeats(){
    return selectedSeats;
  }

  public boolean isSeatBooked(int seatNumber){
    return bookedSeats.contains(seatNumber);
  }

  public void toggleSeatSelection(int seatNumber){
    if (bookedSeats.contains(seatNumber)) return;

    if (selectedSeats.contains(seatNumber)){
      selectedSeats.remove(seatNumber);
    }else {
      selectedSeats.add(seatNumber);
    }
  }

  public void confirmBooking(){
    bookedSeats.addAll(selectedSeats);
    selectedSeats.clear();
  }
}
