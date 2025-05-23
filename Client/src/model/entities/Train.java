package model.entities;

/**
 * Represents a train in the train system.
 * Each train has a unique identifier and can be associated with a schedule
 * that defines its route and timing. The train serves as a vehicle that
 * operates according to predefined schedules between stations.
 *
 * @author Teodora Stoicescu, Bianca Buzdugan, Yelyzaveta Tkachenko
 */
public class Train
{
  private int trainId;
  private Schedule schedule;

  /**
   * Constructs a new Train with the specified train ID.
   * The schedule is initially not set and should be assigned separately.
   *
   * @param trainId the unique identifier for this train
   */
  public Train(int trainId)
  {
    this.trainId = trainId;
  }

  /**
   * Gets the train ID.
   *
   * @return the unique identifier of this train
   */
  public int getTrainId()
  {
    return trainId;
  }

  /**
   * Sets the train ID.
   *
   * @param trainId the new unique identifier for this train
   */
  public void setTrainId(int trainId)
  {
    this.trainId = trainId;
  }

  /**
   * Gets the schedule associated with this train.
   *
   * @return the schedule object, or null if no schedule is assigned
   */
  public Schedule getSchedule()
  {
    return schedule;
  }

  /**
   * Sets the schedule for this train.
   *
   * @param schedule the schedule to associate with this train
   */
  public void setSchedule(Schedule schedule)
  {
    this.schedule = schedule;
  }

  /**
   * Returns a string representation of the Train object.
   * If a schedule is assigned, the format includes train ID, departure station, and arrival station.
   * If no schedule is assigned, it shows the train ID with "No schedule".
   *
   * @return a string representation of this train showing ID and route information
   */
  @Override public String toString()
  {
    if (schedule != null && schedule.getDepartureStation() != null && schedule.getArrivalStation() != null)
    {

      return String.format("Train ID: %d, From: %s, To: %s", trainId, schedule.getDepartureStation().getName(),
          schedule.getArrivalStation().getName());
    }
    else
    {
      return String.format("Train ID: %d, No schedule", trainId);
    }
  }
}