package dtos;

import model.entities.MyDate;

public class TrainDTO
{
  public int trainId;
  public int scheduleId;
  public String from;
  public String to;
  public MyDate departureDate;
  public MyDate arrivalDate;

  public TrainDTO(int trainId,int scheduleId, String from, String to, MyDate departureDate, MyDate arrivalDate){
    this.trainId = trainId;
    this.scheduleId = scheduleId;
    this.from = from;
    this.to = to;
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
  }

  @Override public String toString(){
    return String.format(
        "Train %d: %s → %s at %02d:%02d on %02d/%02d/%04d",
        trainId,
        from,
        to,
        departureDate.hour, departureDate.minute,
        departureDate.day, departureDate.month, departureDate.year,
        arrivalDate.hour, arrivalDate.minute,
        arrivalDate.day, arrivalDate.month, arrivalDate.year
    );
  }
}
