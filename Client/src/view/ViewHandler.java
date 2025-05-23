package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import session.Session;
import view.admin.add.AddScheduleViewController;
import view.admin.main.MainAdminViewController;
import view.admin.modify.ModifyViewController;
import view.admin.myAccount.AdminMyAccountViewController;
import view.traveller.confirm.ConfirmTicketViewController;
import view.traveller.search.SearchTicketViewController;
import view.traveller.seat.SeatSelectionViewController;
import view.traveller.trains.ChooseTrainViewController;
import view.front.FrontViewController;
import view.login.LoginViewController;
import view.register.RegisterViewController;
import view.traveller.myAccount.TravellerMyAccountViewController;
import view.traveller.upcomingDepartures.UpcomingDepartureViewController;
import view.traveller.previousDepartures.PreviousDeparturesViewController;
import viewmodel.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewHandler
{
  public enum ViewType
  {
    FRONT, REGISTER, LOGIN, LOGGEDIN_ADMIN, LOGGEDIN_USER, ADMIN_ACCOUNT, USER_ACCOUNT, ADD_SCHEDULE, MODIFY_TRAIN, CHOOSE_TRAIN, SEAT_SELECTION, CONFIRM_TICKET, UPCOMING, PREVIOUS
  }

  private static Stage stage;
  private static ViewType previousView;
  private static SearchTicketVM searchTicketVM;

  private static Map<String, Object> dataStore = new HashMap<>();

  public static void start(Stage s)
  {
    stage = s;
    showView(ViewType.FRONT);
    stage.show();
  }

  public static void setData(String key, Object value)
  {
    dataStore.put(key, value);
  }

  public static Object getData(String key)
  {
    return dataStore.get(key);
  }

  public static void clearData(String key)
  {
    dataStore.remove(key);
  }

  public static void showView(ViewType view)
  {
    try
    {
      previousView = view;

      switch (view)
      {
        case FRONT -> showFrontView();
        case REGISTER -> showRegisterView();
        case LOGIN -> showLoginView();
        case LOGGEDIN_ADMIN -> showLoggedInAdminView();
        case ADMIN_ACCOUNT -> showAdminAccountView();
        case ADD_SCHEDULE -> showAddScheduleView();
        case MODIFY_TRAIN -> showModifyTrainView();
        case LOGGEDIN_USER -> showLoggedInUserView();
        case USER_ACCOUNT -> showUserAccountView();
        case CHOOSE_TRAIN -> showChooseTrainView();
        case SEAT_SELECTION -> showChooseSeatSelectionView();
        case CONFIRM_TICKET -> showChooseConfirmTicketView();
        case UPCOMING -> showUpcomingDeparturesView();
        case PREVIOUS -> showPreviousDeparturesView();
      }
    }
    catch (Exception e)
    {
      System.out.println("Error showing view: " + e.getMessage());
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
    RegisterVM registerVM = new RegisterVM();
    RegisterViewController controller = new RegisterViewController(registerVM);
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/register/RegisterView.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showLoginView() throws IOException
  {
    LoginVM logInVM = new LoginVM();
    LoginViewController controller = new LoginViewController(logInVM);
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/login/LoginView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showLoggedInAdminView() throws IOException
  {
    MainAdminVM viewModel = new MainAdminVM();
    MainAdminViewController controller = new MainAdminViewController();

    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/admin/main/MainAdminView.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);

    Scene scene = new Scene(fxmlLoader.load());
    controller.init(viewModel);

    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showAdminAccountView() throws IOException
  {
    AdminMyAccountViewController controller = new AdminMyAccountViewController();
    AdminMyAccountVM myAccountVM = new AdminMyAccountVM();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/admin/myAccount/AdminMyAccountView.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    controller.init(myAccountVM);
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showAddScheduleView() throws IOException
  {
    AddScheduleViewController controller = new AddScheduleViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/admin/add/AddScheduleView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());

    if (dataStore.containsKey("addScheduleVM"))
    {
      AddScheduleVM viewModel = (AddScheduleVM) dataStore.get("addScheduleVM");

      controller.init(viewModel);
    }
    else
    {
      AddScheduleVM newViewModel = new AddScheduleVM();
      controller.init(newViewModel);
    }
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showModifyTrainView() throws IOException
  {
    ModifyViewController controller = new ModifyViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/admin/modify/ModifyView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());

    if (dataStore.containsKey("modifyTrainVM"))
    {
      ModifyTrainVM viewModel = (ModifyTrainVM) dataStore.get("modifyTrainVM");
      controller.init(viewModel);
    }

    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showLoggedInUserView() throws IOException
  {
    String email = Session.getInstance().getUserEmail();
    searchTicketVM = new SearchTicketVM(email);
    SearchTicketViewController controller = new SearchTicketViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/search/SearchTicketView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());

    controller.init(searchTicketVM);

    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showChooseTrainView() throws IOException
  {
    ChooseTrainViewController controller = new ChooseTrainViewController();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/trains/ChooseTrainView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());

    ChooseTrainVM chooseTrainVM = new ChooseTrainVM();
    controller.init(chooseTrainVM, searchTicketVM);

    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showChooseSeatSelectionView() throws IOException
  {
    SeatSelectionViewController controller = new SeatSelectionViewController();
    SeatSelectionVM seatSelectionVM = new SeatSelectionVM();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/seat/SeatSelectionView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    controller.init(seatSelectionVM, searchTicketVM);
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showChooseConfirmTicketView() throws IOException
  {
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/confirm/ConfirmTicketView.fxml"));

    Scene scene = new Scene(fxmlLoader.load());
    ConfirmTicketViewController controller = fxmlLoader.getController();
    ConfirmTicketVM confirmTicketVM = new ConfirmTicketVM();
    controller.init(confirmTicketVM);
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showUserAccountView() throws IOException
  {
    TravellerMyAccountViewController controller = new TravellerMyAccountViewController();
    TravellerMyAccountVM myAccountVM = new TravellerMyAccountVM();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/myAccount/TravellerMyAccountView.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    controller.init(myAccountVM);
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showPreviousDeparturesView() throws IOException
  {
    PreviousDeparturesViewController controller = new PreviousDeparturesViewController();
    PreviousDeparturesVM previousDeparturesVM = new PreviousDeparturesVM();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/previousDepartures/PreviousDeparturesView.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    controller.init(previousDeparturesVM);
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }

  private static void showUpcomingDeparturesView() throws IOException
  {
    UpcomingDepartureViewController controller = new UpcomingDepartureViewController();
    UpcomingDeparturesVM upcomingDeparturesVM = new UpcomingDeparturesVM();
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/upcomingDepartures/UpcomingDeparturesView.fxml"));
    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
    controller.init(upcomingDeparturesVM);
    stage.setTitle("VIArail App");
    stage.setScene(scene);
  }
}
