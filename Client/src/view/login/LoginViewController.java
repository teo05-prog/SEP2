package view.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.ViewHandler;
import viewmodel.LoginVM;

public class LoginViewController
{
  private LoginVM viewModel;

  @FXML private TextField emailInput;
  @FXML private PasswordField passwordInput;

  @FXML private Label messageLabel;

  @FXML private Button buttonRegister;
  @FXML private Button buttonLogin;

  public LoginViewController(LoginVM viewModel)
  {
    this.viewModel = viewModel;
  }

  public void initialize()
  {
    emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
    passwordInput.textProperty().bindBidirectional(viewModel.passwordProperty());
    messageLabel.textProperty().bind(viewModel.messageProperty());
    buttonLogin.disableProperty().bind(viewModel.enableLoginButtonProperty());
  }

  public void onRegister(ActionEvent e)
  {
    if (e.getSource() == buttonRegister)
      ViewHandler.showView(ViewHandler.ViewType.REGISTER);
  }

  public void onLogin(ActionEvent e)
  {
    if (e.getSource() == buttonLogin)
    {
      viewModel.loginUser();
    }
  }
}
