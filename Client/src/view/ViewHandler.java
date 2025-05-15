package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import session.Session;
import view.admin.add.AddTrainViewController;
import view.admin.main.MainAdminViewController;
import view.admin.modify.ModifyViewController;
import view.admin.myAccount.AdminMyAccountViewController;
import view.traveller.search.SearchTicketController;
import view.traveller.seat.SeatSelectionController;
import view.traveller.trains.ChooseTrainController;
import view.front.FrontViewController;
import view.login.LoginViewController;
import view.register.RegisterViewController;
import view.traveller.myAccount.TravellerMyAccountViewController;
import view.traveller.upcomingDepartures.UpcomingDepartureController;
//import view.traveller.previousDepartures.PreviousDeparturesController;
import viewmodel.*;

import java.io.IOException;

public class ViewHandler
{
  public enum ViewType
  {
    FRONT, REGISTER, LOGIN, LOGGEDIN_ADMIN, LOGGEDIN_USER, ADMIN_ACCOUNT, USER_ACCOUNT, ADD_TRAIN, MODIFY_TRAIN, CHOOSE_TRAIN, SEAT_SELECTION, CONFIRM_TICKET,UPCOMING,PREVIOUS
  }

  private static Stage stage;
  private static ViewType previousView;
  private static AddTrainVM addTrainVM;
  private static SearchTicketVM searchTicketVM; // do not delete this line, I need it to store and reuse the same SearchTicketVN when moving from Search -> ChooseTrain

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
      System.out.println("Attempting to show view: " + view);
      previousView = view;

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
        case SEAT_SELECTION -> showChooseSeatSelectionView();
        case CONFIRM_TICKET -> showChooseConfirmTicketView();
        case UPCOMING -> showUpcomingDeparturesView();
        //case PREVIOUS -> showPreviousDeparturesView();
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

    if (previousView == ViewType.ADD_TRAIN && addTrainVM != null && addTrainVM.isAddTrainSuccess())
    {
      controller.showAddTrainSuccess();
      addTrainVM = null;
    }

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

  private static void showAddTrainView() throws IOException
  {
    AddTrainViewController controller = new AddTrainViewController();
    AddTrainVM addTrainVM = new AddTrainVM();
    FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("/view/admin/add/AddTrainView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());

    controller.init(addTrainVM);

    stage.setTitle("VIArail App");
    stage.setScene(scene);
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
    String email = Session.getInstance().getUserEmail();
    searchTicketVM = new SearchTicketVM(email);
    SearchTicketController controller = new SearchTicketController();
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
    ChooseTrainController controller = new ChooseTrainController();
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
    SeatSelectionController controller = new SeatSelectionController();
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
    ChooseTrainController controller = new ChooseTrainController();
    // add view model
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("/view/traveller/confirm/ConfirmTicketView.fxml"));

    fxmlLoader.setControllerFactory(ignore -> controller);
    Scene scene = new Scene(fxmlLoader.load());
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
//  private static void showPreviousDeparturesView() throws IOException
//  {
//    PreviousDeparturesController controller = new PreviousDeparturesController();
//    PreviousDeparturesVM previousDeparturesVM = new PreviousDeparturesVM();
//    FXMLLoader fxmlLoader = new FXMLLoader(
//        ViewHandler.class.getResource("/view/traveller/previousDepartures/PreviousDeparturesView.fxml"));
//    fxmlLoader.setControllerFactory(ignore -> controller);
//    Scene scene = new Scene(fxmlLoader.load());
//    controller.init(previousDeparturesVM);
//    stage.setTitle("VIArail App");
//    stage.setScene(scene);
//  }
  private static void showUpcomingDeparturesView() throws IOException
  {
    UpcomingDepartureController controller = new UpcomingDepartureController();
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
