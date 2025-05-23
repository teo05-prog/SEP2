package dtos;

public class AddScheduleDTO
{
  private final int scheduleId;
  private final int trainId;
  private final String departureStation;
  private final String arrivalStation;
  private final String departureDate;
  private final String departureTime;
  private final String arrivalDate;
  private final String arrivalTime;

  public AddScheduleDTO(int scheduleId, int trainId, String departureStation, String arrivalStation,
      String departureDate, String departureTime, String arrivalDate, String arrivalTime)
  {
    this.scheduleId = scheduleId;
    this.trainId = trainId;
    this.departureStation = departureStation;
    this.arrivalStation = arrivalStation;
    this.departureDate = departureDate;
    this.departureTime = departureTime;
    this.arrivalDate = arrivalDate;
    this.arrivalTime = arrivalTime;
  }

  public int getScheduleId()
  {
    return scheduleId;
  }

  public int getTrainId()
  {
    return trainId;
  }

  public String getDepartureStation()
  {
    return departureStation;
  }

  public String getArrivalStation()
  {
    return arrivalStation;
  }

  public String getDepartureDate()
  {
    return departureDate;
  }

  public String getDepartureTime()
  {
    return departureTime;
  }

  public String getArrivalDate()
  {
    return arrivalDate;
  }

  public String getArrivalTime()
  {
    return arrivalTime;
  }
}
