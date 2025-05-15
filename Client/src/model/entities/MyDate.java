package model.entities;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MyDate
{
  private int day;
  private int month;
  private int year;

  private int minute;
  private int hour;

  public MyDate(int day, int month, int year, int hour, int minute)
  {
    this.day = day;
    this.month = month;
    this.year = year;
    this.hour = hour;
    this.minute = minute;
  }

  public MyDate(int year, int month, int day)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public MyDate(Date sqlDate)
  {
    if (sqlDate != null)
    {
      LocalDate localDate = sqlDate.toLocalDate();
      this.year = localDate.getYear();
      this.month = localDate.getMonthValue();
      this.day = localDate.getDayOfMonth();
      this.hour = 0;
      this.minute = 0;
    }
  }

  public int getDay()
  {
    return day;
  }

  public void setDay(int day)
  {
    this.day = day;
  }

  public int getMonth()
  {
    return month;
  }

  public void setMonth(int month)
  {
    this.month = month;
  }

  public int getYear()
  {
    return year;
  }

  public void setYear(int year)
  {
    this.year = year;
  }

  public int getMinute()
  {
    return minute;
  }

  public void setMinute(int minute)
  {
    this.minute = minute;
  }

  public int getHour()
  {
    return hour;
  }

  public void setHour(int hour)
  {
    this.hour = hour;
  }

  public static MyDate today()
  {
    LocalDate currentDate = LocalDate.now();
    int currentDay = currentDate.getDayOfMonth();
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();

    return new MyDate(currentYear, currentMonth, currentDay);
  }

  public boolean isBefore(MyDate other)
  {
    if (year < other.year)
    {
      return true;
    }
    else if (year == other.year)
    {
      if (month < other.month)
      {
        return true;
      }
      else if (month == other.month)
      {
        return day < other.day;
      }
    }
    return false;
  }

  public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }
    MyDate other = (MyDate) obj;

    return day == other.day && month == other.month && year == other.year && hour == other.hour
        && minute == other.minute;
  }

  public MyDate copy()
  {
    return new MyDate(year, month, day, hour, minute);
  }

  public String toString()
  {
    return String.format("%02d/%02d/%04d", day, month, year);
  }

  public Date toSqlDate()
  {
    if (year < 1900 || month < 1 || month > 12 || day < 1 || day > 31)
    {
      throw new IllegalArgumentException("Invalid date values: year=" + year + ", month=" + month + ", day=" + day);
    }
    String dateStr = String.format("%04d-%02d-%02d", year, month, day);
    try
    {
      return java.sql.Date.valueOf(dateStr);
    }
    catch (IllegalArgumentException e)
    {
      throw new IllegalArgumentException(
          "Cannot convert to SQL Date: " + dateStr + ". Please ensure the date is valid.");
    }
  }

  public LocalDateTime toLocalDateTime() {
    return LocalDateTime.of(year, month, day, hour, minute);
  }

  public java.util.Date getDate()
  {
    return java.sql.Date.valueOf(year + "-" + month + "-" + day);
  }
}