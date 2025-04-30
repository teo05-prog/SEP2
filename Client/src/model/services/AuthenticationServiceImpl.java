package model.services;

import model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService
{
  private final List<User> users = new ArrayList<>();

  @Override public String login(LoginRequest request)
  {
    for (User user : users)
    {
      if(user.getEmail().equals(request.getEmail()))
      {
        if(user.getPassword().equals(request.getPassword()))
        {
          return "Ok";
        }
        return "Incorrect password.";
      }
      return "Email not found.";
    }
    return "";
  }

  @Override public String register(User user)
  {
    users.add(user);
    return "success";
  }

}
