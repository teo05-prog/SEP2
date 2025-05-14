package dtos;

import model.MyDate;

public class TrainDTO
{
  public int trainId;
  public String from;
  public String to;
  public MyDate myDate;

  public TrainDTO(int trainId, String from, String to, MyDate myDate){
    this.trainId = trainId;
    this.from = from;
    this.to = to;
    this.myDate = myDate;
  }

  @Override public String toString(){
    return String.format(
        "Train %d: %s → %s at %02d:%02d on %02d/%02d/%04d",
        trainId,
        from,
        to,
        myDate.hour, myDate.minute,
        myDate.day, myDate.month, myDate.year
    );
  }
}
