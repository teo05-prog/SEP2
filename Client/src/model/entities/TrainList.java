package model.entities;

import java.util.ArrayList;

/**
 * A collection class that manages a list of trains in the train system.
 * This class provides functionality to add, remove, and display trains.
 * It acts as a container for Train objects and provides convenient methods
 * for train management operations.
 *
 * @author Yelyzaveta Tkachenko
 */
public class TrainList
{
  private ArrayList<Train> trains;

  /**
   * Constructs a new empty TrainList.
   * Initializes the internal ArrayList to store Train objects.
   */
  public TrainList()
  {
    this.trains = new ArrayList<>();
  }

  /**
   * Gets the list of all trains.
   * Returns a direct reference to the internal ArrayList, allowing external modification.
   *
   * @return the ArrayList containing all trains
   */
  public ArrayList<Train> getTrains()
  {
    return trains;
  }

  /**
   * Adds a train to the list.
   * The train is added to the end of the current list.
   *
   * @param train the train to add to the list
   */
  public void addTrain(Train train)
  {
    this.trains.add(train);
  }

  /**
   * Removes a train from the list.
   *
   * @param train the train to remove from the list
   */
  public void removeTrain(Train train)
  {
    this.trains.remove(train);
  }

  /**
   * Gets the number of trains in the list.
   *
   * @return the total number of trains currently in the list
   */
  public int size()
  {
    return trains.size();
  }

  /**
   * Returns a string representation of all trains in the list.
   * Each train is displayed on a separate line using its toString() method.
   * If the list is empty, returns "No trains available".
   *
   * @return a string representation of all trains, or a message if no trains exist
   */
  public String toString()
  {
    if (trains.isEmpty())
    {
      return "No trains available";
    }

    StringBuilder sb = new StringBuilder();
    for (Train train : trains)
    {
      sb.append(train.toString()).append("\n");
    }
    return sb.toString();
  }
}