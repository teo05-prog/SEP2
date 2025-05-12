package dtos;

import model.entities.User;

public interface AuthenticationService
{
  public abstract String login(LoginRequest request);

  public abstract String register(RegisterRequest request);

  boolean isCurrentUserAdmin();

  String getUserRole(String email);

  User getCurrentUser();
}
