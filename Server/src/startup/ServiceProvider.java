package startup;

import services.AuthenticationService;
import persistance.search.SearchDAO;
import persistance.search.SearchPostgresDAO;
import services.AuthenticationServiceImpl;
import network.requestHandlers.*;
import persistance.user.UserDAO;
import persistance.user.UserPostgresDAO;
import services.search.SearchService;
import services.search.SearchServiceImpl;
import services.user.UserService;
import services.user.UserServiceImpl;
import utilities.LogLevel;
import utilities.Logger;

import java.sql.SQLException;

public class ServiceProvider
{
  private static ServiceProvider instance;

  // Logger
  private final Logger logger;

  // DAOs
  private final UserDAO userDAO;
  private final SearchDAO searchDAO;

  // Services
  private final UserService userService;
  private final AuthenticationService authService;
  private final SearchService searchService;

  // Request Handlers
  private final RegisterRequestHandler registerRequestHandler;
  private final LoginRequestHandler loginRequestHandler;
  private final SearchRequestHandler searchRequestHandler;

  // Constructor
  private ServiceProvider() throws SQLException
  {
    // Initialize Logger
    this.logger = new Logger(LogLevel.DEBUG);

    //initialize DAO singleton before using it
    SearchPostgresDAO.init(logger);

    // Initialize DAOs
    this.userDAO = UserPostgresDAO.getInstance();
    this.searchDAO = SearchPostgresDAO.getInstance();

    // Initialize Services
    this.userService = new UserServiceImpl(userDAO);
    this.authService = new AuthenticationServiceImpl(userDAO, userService);
    this.searchService = new SearchServiceImpl(searchDAO, logger);

    // Initialize Request Handlers
    this.registerRequestHandler = new RegisterRequestHandler(authService);
    this.loginRequestHandler = new LoginRequestHandler(authService,logger);
    this.searchRequestHandler = new SearchRequestHandler(searchService,logger);
  }

  public static synchronized ServiceProvider getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new ServiceProvider();
    }
    return instance;
  }

  // Getters for logger
  public Logger getLogger()
  {
    return logger;
  }

  // Getters for DAOs
  public UserDAO getUserDAO()
  {
    return userDAO;
  }

  // Getters for Services
  public UserService getUserService()
  {
    return userService;
  }

  public AuthenticationService getAuthService()
  {
    return authService;
  }

  // Getters for Request Handlers
  public RegisterRequestHandler getRegisterRequestHandler()
  {
    return registerRequestHandler;
  }

  public LoginRequestHandler getLoginRequestHandler()
  {
    return loginRequestHandler;
  }


  public RequestHandler getSearchRequestHandler()
  {
    return searchRequestHandler;
  }


  // Stub methods for other handlers that may be implemented later
  public RequestHandler getTrainsRequestHandler()
  {
    throw new UnsupportedOperationException("Trains handler not implemented yet");
  }

  public RequestHandler getSeatRequestHandler()
  {
    throw new UnsupportedOperationException("Seat handler not implemented yet");
  }

  public RequestHandler getConfirmRequestHandler()
  {
    throw new UnsupportedOperationException("Confirm handler not implemented yet");
  }

  public RequestHandler getAddRequestHandler()
  {
    throw new UnsupportedOperationException("Add handler not implemented yet");
  }

  public RequestHandler getMainAdminRequestHandler()
  {
    throw new UnsupportedOperationException("MainAdmin handler not implemented yet");
  }

  public RequestHandler getModifyRequestHandler()
  {
    throw new UnsupportedOperationException("Modify handler not implemented yet");
  }

  public RequestHandler getMyAccountRequestHandler()
  {
    throw new UnsupportedOperationException("MyAccount handler not implemented yet");
  }
}