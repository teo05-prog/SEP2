package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.front.FrontViewController;
import view.register.RegisterViewController;

import java.io.IOException;

public class ViewHandler
{
  public class ViewType
  {
    public static final int FRONT = 0;
    public static final int REGISTER = 1;
    public static final int LOGIN = 2;
    public static final int LOGGEDIN = 3;
  }

  private static Stage stage;

  public static void start(Stage s)
  {
    stage = s;
    showView(ViewType.FRONT);
    stage.show();
  }

  public static void showView(int view)
  {
    try
    {
      switch (view)
      {
        case ViewType.FRONT -> showFrontView();
        case ViewType.REGISTER -> showRegisterView();
        case ViewType.LOGIN -> showLoginView();
        case ViewType.LOGGEDIN -> showLoggedInView();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private static void showFrontView() throws IOException
  {
    FrontViewController controller = new FrontViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/front/Front.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showRegisterView() throws IOException
  {
    RegisterViewController controller = new RegisterViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/register/Register.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showLoginView() throws IOException
  {
    // to be added
  }

  private static void showLoggedInView() throws IOException
  {
    // to be added
  }
}
