package persistance.user;

import model.entities.MyDate;
import model.entities.User;

public interface UserDAO
{
  public void createTraveller(String name, String email, String password, MyDate birthDate);

  public User readByEmail(String email);

  public void deleteUser(String email);

  public void updateAdmin(String name, String email, String password);

  public void updateTraveller(String name, String email, String password, MyDate birthDate);
}
