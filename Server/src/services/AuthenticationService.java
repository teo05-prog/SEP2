package services;

import model.entities.User;
import dtos.LoginRequest;
import dtos.RegisterRequest;

public interface AuthenticationService
{
  String login(LoginRequest request);

  String register(RegisterRequest request);

  boolean isCurrentUserAdmin();

  String getUserRole(String email);

  User getCurrentUser();
}
