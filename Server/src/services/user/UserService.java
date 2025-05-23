package services.user;

import dtos.RegisterDTO;
import model.entities.User;

public interface UserService
{
  User createTraveller(RegisterDTO request) throws Exception;
}
