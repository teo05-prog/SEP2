package model.services;

import model.entities.User;

public interface AuthenticationService
{
  public abstract String login(LoginRequest request);

  public abstract String register(User user);
}
