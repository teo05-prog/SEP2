package services;

import dtos.AuthenticationService;
import dtos.LoginRequest;
import dtos.RegisterRequest;
import model.entities.User;
import persistance.user.UserDAO;
import services.user.UserService;

public class AuthenticationServiceImpl implements AuthenticationService
{
  private final UserDAO userDAO;
  private final UserService userService;

  public AuthenticationServiceImpl(UserDAO userDAO, UserService userService)
  {
    this.userDAO = userDAO;
    this.userService = userService;
  }

  @Override public String login(LoginRequest request)
  {
    User user = userDAO.readByEmail(request.getEmail());
    if (user == null)
    {
      return "Email not found.";
    }
    if (!user.getPassword().equals(request.getPassword()))
    {
      return "Incorrect password.";
    }
    return "Ok";
  }

  @Override public String register(RegisterRequest request)
  {
    // Validate name
    if (request.getName() == null || request.getName().isEmpty())
    {
      return "Name cannot be empty";
    }

    // Validate email
    if (request.getEmail() == null || request.getEmail().isEmpty() || !request.getEmail().contains("@"))
    {
      return "Invalid email address";
    }

    // Validate password (assuming minimum 8 characters)
    if (request.getPassword() == null || request.getPassword().length() < 8)
    {
      return "Password must be at least 8 characters long";
    }

    // Check if email already exists
    User existingUser = userDAO.readByEmail(request.getEmail());
    if (existingUser != null)
    {
      return "A user with this email already exists";
    }

    // Create traveller
    try
    {
      userService.createTraveller(request);
      return "Success";
    }
    catch (Exception e)
    {
      return "Registration failed: " + e.getMessage();
    }
  }
}