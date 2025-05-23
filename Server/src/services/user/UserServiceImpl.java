package services.user;

import dtos.RegisterDTO;
import model.entities.MyDate;
import model.entities.Traveller;
import model.entities.User;
import persistance.user.UserDAO;

public class UserServiceImpl implements UserService
{
  private final UserDAO userDao;

  public UserServiceImpl(UserDAO userDao)
  {
    this.userDao = userDao;
  }

  @Override public User createTraveller(RegisterDTO request) throws Exception
  {
    MyDate birthday = request.getBirthday();
    Traveller traveller = new Traveller(request.getName(), request.getEmail(), request.getPassword(), birthday);

    userDao.createTraveller(traveller.getName(), traveller.getEmail(), traveller.getPassword(), birthday);
    return traveller;
  }
}
