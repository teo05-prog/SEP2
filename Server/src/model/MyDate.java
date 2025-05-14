package model;

public class MyDate
{
  public int day;
  public int month;
  public int year;
  public int hour;
  public int minute;

  public MyDate(int day, int month, int year, int hour, int minute) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.hour = hour;
    this.minute = minute;
  }

  public java.sql.Date toSqlDate() {
    return java.sql.Date.valueOf(java.time.LocalDate.of(year, month, day));
  }

  public java.sql.Time toSqlTime() {
    return java.sql.Time.valueOf(java.time.LocalTime.of(hour, minute));
  }
}
