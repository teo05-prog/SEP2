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
  @FXML private CheckBox adminCheckBox;

  @FXML private Label messageLabel;

  @FXML private Button buttonRegister;
  @FXML private Button buttonLogin;

  public LoginViewController(LoginVM viewModel)
  {
    this.viewModel = viewModel;
  }

  public void initialize()
  {
    this.viewModel = viewModel;
    emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
    passwordInput.textProperty().bindBidirectional(viewModel.passwordProperty());
    adminCheckBox.selectedProperty().bindBidirectional(viewModel.isAdminProperty());

    messageLabel.textProperty().bind(viewModel.messageProperty());

    buttonLogin.disableProperty().bind(viewModel.enableLoginButtonProperty());

    viewModel.loginSucceededProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue)
      {
        // Check if admin is selected and show appropriate view
        if (adminCheckBox.isSelected())
        {
          ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_ADMIN);
        }
        else
        {
          ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
        }
      }
    }));
  }

  public void onRegister(ActionEvent e)
  {
    if (e.getSource() == buttonRegister)
      ViewHandler.showView(ViewHandler.ViewType.REGISTER);
  }

  public void onLogin(ActionEvent e)
  {
    if (e.getSource() == buttonLogin)
      viewModel.loginUser();
  }
}
