package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.services.AuthenticationService;
import model.services.AuthenticationServiceImpl;
import view.front.FrontViewController;
import view.register.RegisterViewController;
import viewmodel.RegisterVM;

import java.io.IOException;

public class ViewHandler
{
  public enum ViewType
  {
    FRONT, REGISTER, LOGIN, LOGGEDIN
  }

  private static AuthenticationService authService = new AuthenticationServiceImpl();
  private static Stage stage;

  public static void start(Stage s)
  {
    stage = s;
    showView(ViewType.FRONT);
    stage.show();
  }

  public static void showView(ViewType view)
  {
    try
    {
      switch (view)
      {
        case FRONT -> showFrontView();
        case REGISTER -> showRegisterView();
        case LOGIN -> showLoginView();
        case LOGGEDIN -> showLoggedInView();
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
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/front/FrontView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showRegisterView() throws IOException
  {
    RegisterVM registerVM = new RegisterVM(authService);
    RegisterViewController controller = new RegisterViewController(registerVM);
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/register/RegisterView.fxml"));
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
