package startup;

import services.AuthenticationService;
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
    ViewHandler.start(primaryStage);
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}