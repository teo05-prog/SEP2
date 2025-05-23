package view.traveller.myAccount;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.ViewHandler;
import viewmodel.TravellerMyAccountVM;

public class TravellerMyAccountViewController
{
  private TravellerMyAccountVM viewModel;

  @FXML private Button startButton;
  @FXML private Button previousButton;
  @FXML private Button upcomingButton;
  @FXML private Button myAccountButton;
  @FXML private Label nameLabel;
  @FXML private Label birthdayLabel;
  @FXML private Label emailLabel;

  public void init(TravellerMyAccountVM viewModel)
  {
    this.viewModel = viewModel;
    setupBindings();
    viewModel.refresh();
  }

  @FXML public void initialize()
  {
    myAccountButton.setDisable(true);
  }

  private void setupBindings()
  {
    nameLabel.textProperty().bind(viewModel.nameProperty());
    birthdayLabel.textProperty().bind(viewModel.birthdayProperty());
    emailLabel.textProperty().bind(viewModel.emailProperty());
  }

  @FXML private void onStartButton(ActionEvent e)
  {
    if (e.getSource() == startButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
    }
  }

  @FXML private void onPreviousButton(ActionEvent e)
  {
    if (e.getSource() == previousButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.PREVIOUS);
    }
  }

  @FXML private void onUpcomingButton(ActionEvent e)
  {
    if (e.getSource() == upcomingButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.UPCOMING);
    }
  }

  @FXML private void onMyAccountButton()
  {
    //do nothing because we are already on this page
  }
}
