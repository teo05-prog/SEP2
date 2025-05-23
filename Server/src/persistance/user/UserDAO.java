package persistance.user;

import model.entities.MyDate;
import model.entities.User;

public interface UserDAO
{
  void createTraveller(String name, String email, String password, MyDate birthDate);
  User readByEmail(String email);
  void deleteUser(String email);
}
