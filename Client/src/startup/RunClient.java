package startup;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;

public class RunClient extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    ViewHandler.start(primaryStage);
  }
}