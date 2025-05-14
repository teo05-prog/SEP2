package services.user;

import dtos.RegisterRequest;
import model.entities.User;

public interface UserService
{
  User createTraveller(RegisterRequest request) throws Exception;

  void deleteTraveller(RegisterRequest request) throws Exception;
}
