package services.user;

import dtos.RegisterRequest;

public interface UserService
{
  void createTraveller(RegisterRequest request) throws Exception;

  void deleteTraveller(RegisterRequest request) throws Exception;
}
