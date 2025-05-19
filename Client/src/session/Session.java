package session;

import dtos.TrainDTO;
import model.entities.Ticket;

public class Session
{
  private static Session instance;
  private String userEmail;
  private TrainDTO selectedTrainDTO;
  private boolean isAdmin;
  private String userName;
  private String birthday;
  private Ticket currentTicket;

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
    isAdmin = false;
  }

  public void setSelectedTrainDTO(TrainDTO selectedTrain)
  {
    this.selectedTrainDTO = selectedTrain;
  }

  public TrainDTO getSelectedTrainDTO()
  {
    return selectedTrainDTO;
  }

  public void setIsAdmin(boolean isAdmin)
  {
    this.isAdmin = isAdmin;
  }

  public boolean isAdmin()
  {
    return isAdmin;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getUserName()
  {
    return userName;
  }

  public String getBirthday()
  {
    return birthday;
  }

  public void setBirthday(String birthday)
  {
    this.birthday = birthday;
  }

  public void setCurrentTicket(Ticket currentTicket)
  {
    this.currentTicket = currentTicket;
  }

  public Ticket getCurrentTicket()
  {
    return currentTicket;
  }
}
