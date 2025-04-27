package model.services;

import model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService
{
  private final List<User> users = new ArrayList<>();

  @Override public String register(User user)
  {
    users.add(user);
    return "success";
  }
}
