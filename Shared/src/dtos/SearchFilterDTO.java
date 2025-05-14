package dtos;

import model.MyDate;

import java.io.Serializable;

public class SearchFilterDTO implements Serializable
{
  public String userEmail;
  public String from;
  public String to;
  public MyDate myDate;
  public boolean seat;
  public boolean bicycle;

  public SearchFilterDTO(String userEmail,String from, String to, MyDate myDate,
      boolean seat, boolean bicycle){
    this.userEmail = userEmail;
    this.from = from;
    this.to = to;
    this.myDate = myDate;
    this.seat = seat;
    this.bicycle = bicycle;
  }
}
