package services.authentication;

import dtos.LoginDTO;
import dtos.RegisterDTO;
import model.entities.Admin;
import model.entities.User;
import persistance.user.UserDAO;
import services.user.UserService;

public class AuthenticationServiceImpl implements AuthenticationService
{
  private boolean isAdmin = false;
  private User currentUser;
  private final UserDAO userDAO;
  private final UserService userService;

  public AuthenticationServiceImpl(UserDAO userDAO, UserService userService)
  {
    this.userDAO = userDAO;
    this.userService = userService;
  }

  @Override public String login(LoginDTO request)
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
    // Set admin status based on user role
    this.isAdmin = (user instanceof Admin);
    this.currentUser = user;
    return "Ok";
  }

  @Override public String register(RegisterDTO request)
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
      User newUser = userService.createTraveller(request);
      this.currentUser = newUser;
      this.isAdmin = false;
      System.out.println("Registration successful. Current user: " + currentUser);
      return "Success";
    }
    catch (Exception e)
    {
      System.out.println("Registration failed: " + e.getMessage());
      return "Registration failed: " + e.getMessage();
    }
  }

  @Override public String getUserRole(String email)
  {
    User user = userDAO.readByEmail(email);
    if (user != null)
    {
      return (user instanceof Admin) ? "ADMIN" : "USER";
    }
    return null;
  }
}