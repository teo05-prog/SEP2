package view.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import view.ViewHandler;
import viewmodel.RegisterVM;

public class RegisterViewController
{
  @FXML private RegisterVM viewModel;

  @FXML private TextField nameInput;
  @FXML private DatePicker birthDateInput;
  @FXML private TextField emailInput;
  @FXML private PasswordField passwordInput;
  @FXML private PasswordField repeatPasswordInput;

  @FXML private Label messageLabel;

  @FXML private Button buttonRegister;
  @FXML private Button buttonLogin;

  public RegisterViewController(RegisterVM vm)
  {
    this.viewModel = vm;
  }

  public void initialize()
  {
    nameInput.textProperty().bindBidirectional(viewModel.nameProperty());
    birthDateInput.valueProperty().bindBidirectional(viewModel.birthDateProperty());
    emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
    passwordInput.textProperty().bindBidirectional(viewModel.passwordProperty());
    repeatPasswordInput.textProperty().bindBidirectional(viewModel.repeatPasswordProperty());

    messageLabel.textProperty().bind(viewModel.messageProperty());

    buttonRegister.disableProperty().bind(viewModel.enableRegisterButtonProperty());
  }

  public void onRegister(ActionEvent e)
  {
    if (e.getSource() == buttonRegister)
      viewModel.registerUser();
  }

  public void onLogin(ActionEvent e)
  {
    if (e.getSource() == buttonLogin)
      ViewHandler.showView(ViewHandler.ViewType.LOGIN);
  }
}
