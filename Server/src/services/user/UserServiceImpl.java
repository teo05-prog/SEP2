package services.user;

import dtos.RegisterRequest;
import model.entities.Traveller;
import persistance.user.UserDAO;

public class UserServiceImpl implements UserService
{
  private final UserDAO userDao;

  public UserServiceImpl(UserDAO userDao)
  {
    this.userDao = userDao;
  }

  @Override public void createTraveller(RegisterRequest request) throws Exception
  {
    Traveller traveller = new Traveller(request.getName(), request.getEmail(), request.getPassword(), request.getBirthday());

    userDao.createTraveller(traveller.getName(), traveller.getEmail(), traveller.getPassword(),
        traveller.getBirthDate());
  }

  @Override public void deleteTraveller(RegisterRequest request) throws Exception
  {
    Traveller traveller = new Traveller(request.getName(), request.getEmail(), request.getPassword(), request.getBirthday());

    userDao.deleteUser(traveller.getEmail());
  }
}
