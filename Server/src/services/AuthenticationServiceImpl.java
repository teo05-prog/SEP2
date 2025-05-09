package services;

import dtos.AuthenticationService;
import dtos.LoginRequest;
import dtos.RegisterRequest;
import dtos.error.TravellerRequest;
import model.entities.User;
import persistance.user.UserDAO;
import services.user.UserService;

import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService
{
  private final UserDAO userDAO;
  private final UserService userService;

  public AuthenticationServiceImpl() throws SQLException
  {
    this.userDAO = new persistance.user.UserPostgresDAO();
    this.userService = new services.user.UserServiceImpl(userDAO);
  }

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

    if (user.getPassword().equals(request.getPassword()))
    {
      return "Ok";
    }

    return "Incorrect password.";
  }

  @Override public String register(RegisterRequest request)
  {
    if (request.getName() == null || request.getName().trim().isEmpty())
    {
      return "Name cannot be empty";
    }

    if (request.getEmail() == null || request.getEmail().trim().isEmpty() || !isValidEmail(request.getEmail()))
    {
      return "Invalid email address";
    }

    if (request.getPassword() == null || request.getPassword().length() < 6)
    {
      return "Password must be at least 8 characters long";
    }

    // Check if user already exists
    User existingUser = userDAO.readByEmail(request.getEmail());
    if (existingUser != null)
    {
      return "A user with this email already exists";
    }

    try
    {
      // Convert RegisterRequest to TravellerRequest
      TravellerRequest travellerRequest = new TravellerRequest(request.getName(), request.getEmail(),
          request.getPassword(), request.getBirthday());

      // Create the user
      userService.createTraveller(travellerRequest);
      return "Success";
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return "Registration failed: " + e.getMessage();
    }
  }

  private boolean isValidEmail(String email)
  {
    // Simple email validation
    return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
  }
}
