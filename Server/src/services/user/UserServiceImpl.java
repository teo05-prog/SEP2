package services.user;

import dtos.error.TravellerRequest;
import model.entities.Traveller;
import persistance.user.UserDAO;

public class UserServiceImpl implements UserService
{
  private final UserDAO userDao;

  public UserServiceImpl(UserDAO userDao)
  {
    this.userDao = userDao;
  }

  @Override public void createTraveller(TravellerRequest request) throws Exception
  {
    Traveller traveller = new Traveller(request.name(), request.email(), request.password(), request.birthDate());

    userDao.createTraveller(traveller.getName(), traveller.getEmail(), traveller.getPassword(), traveller.getBirthDate());
  }

  @Override public void deleteTraveller(TravellerRequest request) throws Exception
  {
    Traveller traveller = new Traveller(request.name(), request.email(), request.password(), request.birthDate());

    userDao.deleteUser(traveller.getEmail());
  }
}
