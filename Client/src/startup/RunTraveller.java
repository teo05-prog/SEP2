package startup;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;

public class RunTraveller extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    ViewHandler.start(primaryStage);
    ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
  }
  public static void main(String[] args)
  {
    launch();
  }
}
