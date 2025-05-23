package dtos;

public class AddTrainDTO
{
  private final int trainId;

  public AddTrainDTO(int trainId)
  {
    this.trainId = trainId;
  }

  public int getTrainId()
  {
    return trainId;
  }
}
