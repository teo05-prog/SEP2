package session;

import dtos.TrainDTO;

public class Session
{
  private static Session instance;
  private String userEmail;
  private TrainDTO selectedTrainDTO;

  private Session()
  {
  }

  public static synchronized Session getInstance()
  {
    if (instance == null)
    {
      instance = new Session();
    }
    return instance;
  }

  public String getUserEmail()
  {
    return userEmail;
  }

  public void setUserEmail(String userEmail)
  {
    this.userEmail = userEmail;
  }

  public void clear()
  {
    userEmail = null;
  }

  public void setSelectedTrainDTO(TrainDTO selectedTrain)
  {
    this.selectedTrainDTO = selectedTrain;
  }

  public TrainDTO getSelectedTrainDTO()
  {
    return selectedTrainDTO;
  }
}
