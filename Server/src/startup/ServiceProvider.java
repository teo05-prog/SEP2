package startup;

import persistance.admin.ScheduleDAO;
import persistance.admin.SchedulePostgresDAO;
import persistance.admin.TrainDAO;
import persistance.admin.TrainPostgresDAO;
import persistance.seat.SeatDAO;
import persistance.seat.SeatPostgresDAO;
import persistance.ticket.TicketDAO;
import persistance.ticket.TicketPostgresDAO;
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
import services.seat.SeatService;
import services.seat.SeatServiceImpl;
import services.ticket.TicketService;
import services.ticket.TicketServiceImpl;
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
  private final SeatDAO seatDAO;
  private final TicketDAO ticketDAO;

  // Services
  private final UserService userService;
  private final AuthenticationService authService;
  private final SearchService searchService;
  private final TrainService trainService;
  private final ScheduleService scheduleService;
  private final SeatService seatService;
  private final TicketService ticketService;

  // Request Handlers
  private final RegisterRequestHandler registerRequestHandler;
  private final LoginRequestHandler loginRequestHandler;
  private final SearchRequestHandler searchRequestHandler;
  private final TrainsRequestHandler trainsRequestHandler;
  private final SchedulesRequestHandler schedulesRequestHandler;
  private final UserDetailsRequestHandler userDetailsRequestHandler;
  private final SeatRequestHandler seatRequestHandler;
  private final TicketsRequestHandler ticketsRequestHandler;
  private final ModifyRequestHandler modifyRequestHandler;
  private final AddTrainRequestHandler addTrainRequestHandler;
  private final AddScheduleRequestHandler addScheduleRequestHandler;

  private ServiceProvider() throws SQLException
  {
    // Initialize Logger
    this.logger = new Logger(LogLevel.DEBUG);

    //initialize DAO singleton before using it
    SearchPostgresDAO.init(logger);
    SeatPostgresDAO.init(logger);

    // Initialize DAOs
    this.userDAO = UserPostgresDAO.getInstance();
    this.searchDAO = SearchPostgresDAO.getInstance();
    this.trainDAO = TrainPostgresDAO.getInstance();
    this.scheduleDAO = SchedulePostgresDAO.getInstance();
    this.seatDAO = SeatPostgresDAO.getInstance();
    this.ticketDAO = TicketPostgresDAO.getInstance();

    // Initialize Services
    this.userService = new UserServiceImpl(userDAO);
    this.authService = new AuthenticationServiceImpl(userDAO, userService);
    this.searchService = new SearchServiceImpl(searchDAO, logger);
    this.trainService = new TrainServiceImpl(trainDAO);
    this.scheduleService = new ScheduleServiceImpl(scheduleDAO);
    this.seatService = new SeatServiceImpl(seatDAO);
    this.ticketService = new TicketServiceImpl();

    // Initialize Request Handlers
    this.registerRequestHandler = new RegisterRequestHandler(authService);
    this.loginRequestHandler = new LoginRequestHandler(authService,logger);
    this.searchRequestHandler = new SearchRequestHandler(searchService,logger);
    this.trainsRequestHandler = new TrainsRequestHandler(trainService, logger);
    this.schedulesRequestHandler = new SchedulesRequestHandler(scheduleService);
    this.userDetailsRequestHandler = new UserDetailsRequestHandler(userDAO);
    this.seatRequestHandler = new SeatRequestHandler(seatService,logger);
    this.ticketsRequestHandler = new TicketsRequestHandler(ticketService);
    this.modifyRequestHandler = new ModifyRequestHandler(trainService, scheduleService, logger);
    this.addTrainRequestHandler = new AddTrainRequestHandler(trainService, logger);
    this.addScheduleRequestHandler = new AddScheduleRequestHandler(trainService, scheduleService, logger);
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

  public SearchService getSearchService()
  {
    return searchService;
  }

  public TrainService getTrainService()
  {
    return trainService;
  }

  public ScheduleService getScheduleService()
  {
    return scheduleService;
  }

  public SeatService getSeatService()
  {
    return seatService;
  }

  public TicketService getTicketService()
  {
    return ticketService;
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

  public RequestHandler getUserDetailsRequestHandler()
  {
    return userDetailsRequestHandler;
  }


  public RequestHandler getSeatRequestHandler()
  {
    return seatRequestHandler;
  }

  public RequestHandler getTicketRequestHandler()
  {
    return ticketsRequestHandler;
  }

  public RequestHandler getModifyRequestHandler()
  {
    return modifyRequestHandler;
  }

  public RequestHandler getAddTrainRequestHandler()
  {
    return addTrainRequestHandler;
  }

  public RequestHandler getAddScheduleRequestHandler()
  {
    return schedulesRequestHandler;
  }
}