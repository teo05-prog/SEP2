package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.HashSet;
import java.util.Set;

public class SeatSelectionVM
{
  private final ObservableSet<Integer> selectedSeats = FXCollections.observableSet(new HashSet<>());
  private final Set<Integer> bookedSeats = new HashSet<>();

  public ObservableSet<Integer> getSelectedSeats()
  {
    return selectedSeats;
  }

  public boolean isSeatBooked(int seatNumber)
  {
    return bookedSeats.contains(seatNumber);
  }

  public void toggleSeatSelection(int seatNumber)
  {
    if (bookedSeats.contains(seatNumber))
      return;

    boolean isSeat = seatNumber >= 1 && seatNumber <= 16;
    boolean isBicycle = seatNumber >= 17 && seatNumber <= 18;

    // deselect if already selected
    if (selectedSeats.contains(seatNumber))
    {
      selectedSeats.remove(seatNumber);
      return;
    }

    //enforce single seat selection
    if (isSeat)
    {
      selectedSeats.removeIf(n -> n >= 1 && n <= 16);
    }
    //enforce single bicycle seat selection
    if (isBicycle)
    {
      selectedSeats.removeIf(n -> n >= 17 && n <= 18);
    }

    selectedSeats.add(seatNumber);
  }

  public void confirmBooking()
  {
    bookedSeats.addAll(selectedSeats);
    selectedSeats.clear();
  }
}
