package persistance.seat;

import java.util.List;

public interface SeatDAO
{
  List<Integer> getBookedSeatsForTrain(int trainId);
}
