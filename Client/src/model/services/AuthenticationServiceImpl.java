package model.services;

import model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService
{
  private final List<User> users = new ArrayList<>();

  @Override public String login(LoginRequest request) {
    for (User user : users) {
      if (user.getEmail().equals(request.getEmail())) {
        if (user.getPassword().equals(request.getPassword())) {
          return "Ok";
        }
        return "Incorrect password.";
      }
    }
    return "Email not found.";
  }

  @Override
  public String register(RegisterRequest registerRequest)
  {
    if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())){
      return "Passwords do not match";
    }
    for (User user: users){
      if (user.getEmail().equals(registerRequest.getEmail())){
        return "Email already registered";
      }
    }
//    User user = new User(
//        registerRequest.getName(),
//        registerRequest.getEmail(),
//        registerRequest.getPassword(),
//        registerRequest.getBirthday()
//    );
//    users.add(user);
    return "Success";
  }

}
