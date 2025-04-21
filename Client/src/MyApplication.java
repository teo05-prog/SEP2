import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;

public class MyApplication extends Application
{

  @Override public void start(Stage primaryStage) throws Exception
  {
    ViewHandler.start(primaryStage);
  }
}
