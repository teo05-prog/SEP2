package services.authentication;

import dtos.LoginDTO;
import dtos.RegisterDTO;

public interface AuthenticationService
{
  String login(LoginDTO request);

  String register(RegisterDTO request);

  String getUserRole(String email);
}
