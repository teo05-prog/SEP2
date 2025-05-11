package startup;

import dtos.AuthenticationService;
import javafx.application.Application;
import javafx.stage.Stage;
import persistance.user.UserDAO;
import persistance.user.UserPostgresDAO;
import services.AuthenticationServiceImpl;
import services.user.UserService;
import services.user.UserServiceImpl;
import view.ViewHandler;

public class RunClient extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    UserDAO userDAO = new UserPostgresDAO();
    UserService userService = new UserServiceImpl(userDAO);
    AuthenticationService authService = new AuthenticationServiceImpl(userDAO, userService);
    ViewHandler.setAuthService(authService);
    ViewHandler.start(primaryStage);
  }
}