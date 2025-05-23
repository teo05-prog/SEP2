package model.entities;

/**
 * Represents a station in the train system.
 * A station is identified by its name and serves as a departure or arrival point
 * for scheduled trains. This is a simple entity class that
 * primarily stores the station's name.
 *
 * @author Yelyzaveta Tkachenko
 */
public class Station
{
  private String name;

  /**
   * Constructs a new Station with the specified name.
   *
   * @param name the name of the station
   */
  public Station(String name)
  {
    this.name = name;
  }

  /**
   * Gets the name of the station.
   *
   * @return the station name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the name of the station.
   *
   * @param name the new name for the station
   */
  public void setName(String name)
  {
    this.name = name;
  }
}