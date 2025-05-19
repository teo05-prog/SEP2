package services.seat;

import java.util.List;

public interface SeatService
{
  List<Integer> getBookedSeatsForTrain(int trainId);
}
