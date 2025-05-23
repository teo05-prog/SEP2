package model.entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A custom date and time representation class that provides functionality for handling
 * dates and times in the train booking system. This class can represent both
 * date-only and date-time values, and provides conversion methods to work with
 * Java's standard date/time classes.
 * The class stores date components (day, month, year) and optional time components
 * (hour, minute) as separate integer fields for easy manipulation and access.
 *
 * @author Jan Lewek, Teodora Stoicescu, Bianca Buzdugan
 */
public class MyDate
{
  private int day;
  private int month;
  private int year;
  private int minute;
  private int hour;

  /**
   * Constructs a MyDate with specified date and time components.
   * This constructor creates a complete date-time representation.
   *
   * @param day    the day of the month (1-31)
   * @param month  the month of the year (1-12)
   * @param year   the year
   * @param hour   the hour of the day (0-23)
   * @param minute the minute within the hour (0-59)
   */
  public MyDate(int day, int month, int year, int hour, int minute)
  {
    this.day = day;
    this.month = month;
    this.year = year;
    this.hour = hour;
    this.minute = minute;
  }

  /**
   * Constructs a MyDate with only date components.
   *
   * @param year  the year
   * @param month the month of the year (1-12)
   * @param day   the day of the month (1-31)
   */
  public MyDate(int year, int month, int day)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  /**
   * Constructs a MyDate from a SQL Date object.
   * Time components are set to 0 since SQL Date only contains date information.
   *
   * @param sqlDate the SQL Date to convert from, or null
   */
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

  /**
   * Gets the day of the month.
   *
   * @return the day of the month (1-31)
   */
  public int getDay()
  {
    return day;
  }

  /**
   * Sets the day of the month.
   *
   * @param day the day of the month (1-31)
   */
  public void setDay(int day)
  {
    this.day = day;
  }

  /**
   * Gets the month of the year.
   *
   * @return the month of the year (1-12)
   */
  public int getMonth()
  {
    return month;
  }

  /**
   * Sets the month of the year.
   *
   * @param month the month of the year (1-12)
   */
  public void setMonth(int month)
  {
    this.month = month;
  }

  /**
   * Gets the year.
   *
   * @return the year
   */
  public int getYear()
  {
    return year;
  }

  /**
   * Sets the year.
   *
   * @param year the year
   */
  public void setYear(int year)
  {
    this.year = year;
  }

  /**
   * Gets the minute within the hour.
   *
   * @return the minute within the hour (0-59)
   */
  public int getMinute()
  {
    return minute;
  }

  /**
   * Sets the minute within the hour.
   *
   * @param minute the minute within the hour (0-59)
   */
  public void setMinute(int minute)
  {
    this.minute = minute;
  }

  /**
   * Gets the hour of the day.
   *
   * @return the hour of the day (0-23)
   */
  public int getHour()
  {
    return hour;
  }

  /**
   * Sets the hour of the day.
   *
   * @param hour the hour of the day (0-23)
   */
  public void setHour(int hour)
  {
    this.hour = hour;
  }

  /**
   * Creates a MyDate object representing today's date.
   *
   * @return a MyDate object representing the current date
   */
  public static MyDate today()
  {
    LocalDate currentDate = LocalDate.now();
    int currentDay = currentDate.getDayOfMonth();
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();

    return new MyDate(currentYear, currentMonth, currentDay);
  }

  /**
   * Checks if this date is before the specified date.
   *
   * @param other the date to compare against
   * @return true if this date is before the other date, false otherwise
   */
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

  /**
   * Compares this MyDate with the specified object for equality.
   * Two MyDate objects are considered equal if all their date and time components match.
   *
   * @param obj the object to compare with this MyDate
   * @return true if the specified object is equal to this MyDate, false otherwise
   */
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

  /**
   * Creates a copy of this MyDate object.
   *
   * @return a new MyDate object with the same date and time values
   */
  public MyDate copy()
  {
    return new MyDate(year, month, day, hour, minute);
  }

  /**
   * Returns a string representation of this date in DD/MM/YYYY format.
   * Time components are not included in the string representation.
   *
   * @return a formatted string representation of the date
   */
  public String toString()
  {
    return String.format("%02d/%02d/%04d", day, month, year);
  }

  /**
   * Converts this MyDate to a SQL Date object.
   * Only the date components are used; time information is lost.
   *
   * @return a SQL Date representation of this date
   * @throws IllegalArgumentException if the date values are invalid
   */
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

  /**
   * Converts this MyDate to a LocalDateTime object.
   * All date and time components are included in the conversion.
   *
   * @return a LocalDateTime representation of this date and time
   */
  public LocalDateTime toLocalDateTime()
  {
    return LocalDateTime.of(year, month, day, hour, minute);
  }

  /**
   * Converts this MyDate to a SQL Date object.
   * This method provides error handling and debugging information if conversion fails.
   * Only the date components are used; time information is lost.
   *
   * @return a SQL Date representation of this date
   * @throws IllegalArgumentException if the date string cannot be converted to a valid SQL Date
   */
  public Date getDate()
  {
    String dateStr = String.format("%04d-%02d-%02d", year, month, day);
    try
    {
      return Date.valueOf(dateStr);
    }
    catch (IllegalArgumentException e)
    {
      System.err.println("Error creating SQL Date from string: " + dateStr);
      System.err.println("Year: " + year + ", Month: " + month + ", Day: " + day);
      throw e;
    }
  }

  /**
   * Converts this Time to a SQL Time object.
   *
   * @return a SQL Time representation of this time
   */
  public Time toSqlTime()
  {
    return Time.valueOf(LocalTime.of(hour, minute));
  }
}