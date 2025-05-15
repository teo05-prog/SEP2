package startup;

import persistance.admin.ScheduleDAO;
import persistance.admin.SchedulePostgresDAO;
import persistance.admin.TrainDAO;
import persistance.admin.TrainPostgresDAO;
import services.admin.ScheduleService;
import services.admin.ScheduleServiceImpl;
import services.admin.TrainService;
import services.admin.TrainServiceImpl;
import services.authentication.AuthenticationService;
import persistance.search.SearchDAO;
import persistance.search.SearchPostgresDAO;
import services.authentication.AuthenticationServiceImpl;
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
  private final TrainDAO trainDAO;
  private final ScheduleDAO scheduleDAO;

  // Services
  private final UserService userService;
  private final AuthenticationService authService;
  private final SearchService searchService;
  private final TrainService trainService;
  private final ScheduleService scheduleService;

  // Request Handlers
  private final RegisterRequestHandler registerRequestHandler;
  private final LoginRequestHandler loginRequestHandler;
  private final SearchRequestHandler searchRequestHandler;
  private final TrainsRequestHandler trainsRequestHandler;
  private final SchedulesRequestHandler schedulesRequestHandler;

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
    this.trainDAO = TrainPostgresDAO.getInstance();
    this.scheduleDAO = SchedulePostgresDAO.getInstance();

    // Initialize Services
    this.userService = new UserServiceImpl(userDAO);
    this.authService = new AuthenticationServiceImpl(userDAO, userService);
    this.searchService = new SearchServiceImpl(searchDAO, logger);
    this.trainService = new TrainServiceImpl(trainDAO);
    this.scheduleService = new ScheduleServiceImpl(scheduleDAO);

    // Initialize Request Handlers
    this.registerRequestHandler = new RegisterRequestHandler(authService);
    this.loginRequestHandler = new LoginRequestHandler(authService,logger);
    this.searchRequestHandler = new SearchRequestHandler(searchService,logger);
    this.trainsRequestHandler = new TrainsRequestHandler(trainService);
    this.schedulesRequestHandler = new SchedulesRequestHandler(scheduleService);
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
  public RequestHandler getRegisterRequestHandler()
  {
    return registerRequestHandler;
  }

  public RequestHandler getLoginRequestHandler()
  {
    return loginRequestHandler;
  }

  public RequestHandler getSearchRequestHandler()
  {
    return searchRequestHandler;
  }

  public RequestHandler getTrainsRequestHandler()
  {
    return trainsRequestHandler;
  }

  public RequestHandler getSchedulesRequestHandler()
  {
    return schedulesRequestHandler;
  }

  // Stub methods for other handlers that may be implemented later
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
}