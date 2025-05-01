package view.traveller.myAccount;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import viewmodel.MyAccountVM;

public class MyAccountViewController
{
  private MyAccountVM viewModel;

  @FXML private Button startButton;
  @FXML private Button previousButton;
  @FXML private Button upcomingButton;
  @FXML private Button myAccountButton;
  @FXML private Label messageLabel;
  @FXML private Label nameLabel;
  @FXML private Label birthdayLabel;
  @FXML private Label emailLabel;

  public MyAccountViewController()
  {
    this.viewModel = new MyAccountVM();
  }

  public void init(MyAccountVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
    }
  }

  @FXML public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new MyAccountVM();
    }
    setupUI();
  }

  private void setupUI(){
    myAccountButton.setDisable(true);
    updateLabel(nameLabel, viewModel.getName());
    updateLabel(birthdayLabel, viewModel.getBirthday());
    updateLabel(emailLabel, viewModel.getEmail());
  }

  public void updateLabel(Label label, String text){
    label.setText(text);
  }

  @FXML private void onStartButton()
  {
    //later
  }

  @FXML private void onPreviousButton()
  {
    //later
  }

  @FXML private void onUpcomingButton()
  {
    //later
  }

  @FXML private void onMyAccountButton()
  {
    //do nothing because we are already on MyAccount page
  }
}
