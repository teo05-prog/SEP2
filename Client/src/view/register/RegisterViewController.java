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
import model.entities.MyDate;

import java.time.LocalDate;

public class RegisterViewController
{
  private RegisterVM viewModel;

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

    // Handle MyDate to LocalDate conversion with listeners instead of bidirectional binding
    // Initial value from ViewModel to DatePicker
    MyDate initialDate = viewModel.birthDateProperty().get();
    if (initialDate != null)
    {
      LocalDate localDate = LocalDate.of(initialDate.getYear(), initialDate.getMonth(), initialDate.getDay());
      birthDateInput.setValue(localDate);
    }

    // When DatePicker changes, update the ViewModel
    birthDateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        MyDate myDate = new MyDate(newValue.getDayOfMonth(), newValue.getMonthValue(), newValue.getYear());
        viewModel.birthDateProperty().set(myDate);
      }
      else
      {
        viewModel.birthDateProperty().set(null);
      }
    });

    // When ViewModel changes, update the DatePicker
    viewModel.birthDateProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        LocalDate localDate = LocalDate.of(newValue.getYear(), newValue.getMonth(), newValue.getDay());
        birthDateInput.setValue(localDate);
      }
      else
      {
        birthDateInput.setValue(null);
      }
    });

    emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
    passwordInput.textProperty().bindBidirectional(viewModel.passwordProperty());
    repeatPasswordInput.textProperty().bindBidirectional(viewModel.repeatPasswordProperty());

    messageLabel.textProperty().bind(viewModel.messageProperty());

    buttonRegister.disableProperty().bind(viewModel.enableRegisterButtonProperty());

    viewModel.registrationSucceededProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue)
      {
        ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
      }
    }));
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