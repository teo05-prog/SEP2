package services.user;

import dtos.error.TravellerRequest;

public interface UserService
{
  void createTraveller(TravellerRequest request) throws Exception;

  void deleteTraveller(TravellerRequest request) throws Exception;
}
