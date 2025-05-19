package services.seat;

import persistance.seat.SeatDAO;

import java.util.List;

public class SeatServiceImpl implements SeatService
{
  private final SeatDAO seatDAO;

  public SeatServiceImpl(SeatDAO seatDAO)
  {
    this.seatDAO = seatDAO;
  }

  public List<Integer> getBookedSeatsForTrain(int trainId)
  {
    return seatDAO.getBookedSeatsForTrain(trainId);
  }
}
