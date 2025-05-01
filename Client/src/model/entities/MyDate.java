package model.entities;

import java.time.LocalDate;

public class MyDate
{
  private int day;
  private int month;
  private int year;

  private int second;
  private int minute;
  private int hour;

  public MyDate(int day, int month, int year, int hour, int minute, int second)
  {
    this.day = day;
    this.month = month;
    this.year = year;
    this.hour = hour;
    this.minute = minute;
    this.second = second;
  }

  public MyDate(int year, int month, int day)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public MyDate(MyDate date)
  {
    this.day = date.day;
    this.month = date.month;
    this.year = date.year;
    this.hour = date.hour;
    this.minute = date.minute;
    this.second = date.second;
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

  public int getSecond()
  {
    return second;
  }

  public void setSecond(int second)
  {
    this.second = second;
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
    if (other.year < year)
    {
      return true;
    }
    else if (other.month < month && other.year == year)
    {
      return true;
    }
    else if (other.day < day && other.month == month && other.year == year)
    {
      return true;
    }
    else if (other.hour < hour && other.day == day && other.month == month
        && other.year == year)
    {
      return true;
    }
    else if (other.minute < minute && other.hour == hour && other.day == day
        && other.month == month && other.year == year)
    {
      return true;
    }
    else if (other.second < second && other.minute == minute
        && other.hour == hour && other.day == day && other.month == month
        && other.year == year)
    {
      return true;
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

    return day == other.day && month == other.month && year == other.year
        && hour == other.hour && minute == other.minute
        && second == other.second;
  }

  public MyDate copy()
  {
    return new MyDate(year, month, day, hour, minute, second);
  }

  public String toString()
  {
    return String.format("%02d/%02d/%04d %02d:%02d:%02d", day, month, year, hour, minute, second);
  }
}