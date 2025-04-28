package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.services.AuthenticationService;
import model.services.AuthenticationServiceImpl;
import view.admin.main.MainAdminViewController;
import view.admin.modify.ModifyViewController;
import view.buyTicket.search.SearchTicketController;
import view.buyTicket.trains.ChooseTrainController;
import view.front.FrontViewController;
import view.login.LoginViewController;
import view.register.RegisterViewController;
import viewmodel.LoginVM;
import viewmodel.RegisterVM;

import java.io.IOException;

public class ViewHandler
{
  public enum ViewType
  {
    FRONT, REGISTER, LOGIN, LOGGEDIN_ADMIN, LOGGEDIN_USER, ADMIN_ACCOUNT, USER_ACCOUNT, ADD_TRAIN, MODIFY_TRAIN, CHOOSE_TRAIN
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
        case LOGGEDIN_ADMIN -> showLoggedInAdminView();
        case ADMIN_ACCOUNT -> showAdminAccountView();
        case ADD_TRAIN -> showAddTrainView();
        case MODIFY_TRAIN -> showModifyTrainView();
        case LOGGEDIN_USER -> showLoggedInUserView();
        case USER_ACCOUNT -> showUserAccountView();
        case CHOOSE_TRAIN -> showChooseTrainView();
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
    LoginVM logInVM = new LoginVM(authService);
    LoginViewController controller = new LoginViewController(logInVM);
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/login/LoginView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showLoggedInAdminView() throws IOException
  {
    MainAdminViewController controller = new MainAdminViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/admin/main/MainAdminView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showAdminAccountView() throws IOException
  {
    // to be added
  }

  private static void showAddTrainView() throws IOException
  {
    // to be added
  }

  private static void showModifyTrainView() throws IOException
  {
    ModifyViewController controller = new ModifyViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/admin/modify/ModifyView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showLoggedInUserView() throws IOException
  {
    SearchTicketController controller = new SearchTicketController();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/buyTicket/search/SearchTicketView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showChooseTrainView() throws IOException
  {
    ChooseTrainController controller = new ChooseTrainController();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/buyTicket/trains/ChooseTrainView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showUserAccountView() throws IOException
  {
    // to be added
  }
}
