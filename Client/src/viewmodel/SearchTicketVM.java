package viewmodel;

import view.ViewHandler;

import java.time.LocalDate;

public class SearchTicketVM
{
  private String from;
  private String to;
  private LocalDate date;
  private String  time;
  private boolean seat;
  private boolean bicycle;

  public SearchTicketVM(){}

  public void startTrainSearch(String from, String to, LocalDate date, String time, boolean seat, boolean bicycle){
    this.from = from;
    this.to = to;
    this.date = date;
    this.time = time;
    this.seat = seat;
    this.bicycle = bicycle;
    //navigate to the next page
    if(isInputValid()){
      ViewHandler.showView(ViewHandler.ViewType.CHOOSE_TRAIN);
    }else {
      System.out.println("Invalid input. Please check all fields.");
    }
  }
  public boolean isInputValid(){
    return from !=null && !from.isEmpty()
        && to != null && !to.isEmpty()
        && date != null
        && time != null && !time.isEmpty();
  }
  public String getFrom()
  {
    return from;
  }

  public String getTo()
  {
    return to;
  }

  public LocalDate getDate()
  {
    return date;
  }

  public String getTime()
  {
    return time;
  }

  public boolean isSeat()
  {
    return seat;
  }

  public boolean isBicycle()
  {
    return bicycle;
  }
}
