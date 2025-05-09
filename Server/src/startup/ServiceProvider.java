package startup;

import dtos.AuthenticationService;
import model.services.AuthenticationServiceImpl;
import network.requestHandlers.*;
import persistance.user.UserDAO;
import persistance.user.UserPostgresDAO;
import services.user.UserService;
import services.user.UserServiceImpl;
import utilities.LogLevel;
import utilities.Logger;

import java.sql.SQLException;

public class ServiceProvider {
  private static ServiceProvider instance;

  // Logger
  private final Logger logger;

  // DAOs
  private final UserDAO userDAO;

  // Services
  private final UserService userService;
  private final AuthenticationService authService;

  // Request Handlers
  private final RegisterRequestHandler registerRequestHandler;
  private final LoginRequestHandler loginRequestHandler;

  // Constructor
  private ServiceProvider() throws SQLException {
    // Initialize Logger
    this.logger = new Logger(LogLevel.DEBUG);

    // Initialize DAOs
    this.userDAO = UserPostgresDAO.getInstance();

    // Initialize Services
    this.userService = new UserServiceImpl(userDAO);
    this.authService = new AuthenticationServiceImpl(userDAO, userService);

    // Initialize Request Handlers
    this.registerRequestHandler = new RegisterRequestHandler(authService);
    this.loginRequestHandler = new LoginRequestHandler(authService);
  }

  public static synchronized ServiceProvider getInstance() throws SQLException {
    if (instance == null) {
      instance = new ServiceProvider();
    }
    return instance;
  }

  // Getters for logger
  public Logger getLogger() {
    return logger;
  }

  // Getters for DAOs
  public UserDAO getUserDAO() {
    return userDAO;
  }

  // Getters for Services
  public UserService getUserService() {
    return userService;
  }

  public AuthenticationService getAuthService() {
    return authService;
  }

  // Getters for Request Handlers
  public RegisterRequestHandler getRegisterRequestHandler() {
    return registerRequestHandler;
  }

  public LoginRequestHandler getLoginRequestHandler() {
    return loginRequestHandler;
  }

  // Stub methods for other handlers that may be implemented later
  public RequestHandler getSearchRequestHandler() {
    throw new UnsupportedOperationException("Search handler not implemented yet");
  }

  public RequestHandler getTrainsRequestHandler() {
    throw new UnsupportedOperationException("Trains handler not implemented yet");
  }

  public RequestHandler getSeatRequestHandler() {
    throw new UnsupportedOperationException("Seat handler not implemented yet");
  }

  public RequestHandler getConfirmRequestHandler() {
    throw new UnsupportedOperationException("Confirm handler not implemented yet");
  }

  public RequestHandler getAddRequestHandler() {
    throw new UnsupportedOperationException("Add handler not implemented yet");
  }

  public RequestHandler getMainAdminRequestHandler() {
    throw new UnsupportedOperationException("MainAdmin handler not implemented yet");
  }

  public RequestHandler getModifyRequestHandler() {
    throw new UnsupportedOperationException("Modify handler not implemented yet");
  }

  public RequestHandler getMyAccountRequestHandler() {
    throw new UnsupportedOperationException("MyAccount handler not implemented yet");
  }
}