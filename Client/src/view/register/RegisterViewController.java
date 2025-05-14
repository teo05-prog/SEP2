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

  public RegisterViewController(RegisterVM viewModel)
  {
    this.viewModel = viewModel;
  }

  @FXML public void initialize()
  {
    bindProperties();
  }

  private void bindProperties()
  {
    nameInput.textProperty().bindBidirectional(viewModel.nameProperty());
    emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
    passwordInput.textProperty().bindBidirectional(viewModel.passwordProperty());
    repeatPasswordInput.textProperty().bindBidirectional(viewModel.repeatPasswordProperty());

    messageLabel.textProperty().bind(viewModel.messageProperty());
    buttonRegister.disableProperty().bind(viewModel.enableRegisterButtonProperty());

    setupDatePickerBinding();

    viewModel.registrationSucceededProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue)
      {
        ViewHandler.showView(ViewHandler.ViewType.LOGIN);
      }
    }));
  }

  private void setupDatePickerBinding()
  {
    MyDate initialDate = viewModel.birthDateProperty().get();
    if (initialDate != null)
    {
      try
      {
        LocalDate localDate = LocalDate.of(initialDate.getYear(), initialDate.getMonth(), initialDate.getDay());
        birthDateInput.setValue(localDate);
      }
      catch (Exception e)
      {
        System.err.println(
            "Error converting initial date: " + e.getMessage() + " - values: day=" + initialDate.getDay() + ", month="
                + initialDate.getMonth() + ", year=" + initialDate.getYear());
        System.err.println("MyDate object: " + initialDate);
      }
    }

    birthDateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        int day = newValue.getDayOfMonth();
        int month = newValue.getMonthValue();
        int year = newValue.getYear();

        MyDate myDate = new MyDate(year, month, day);

        viewModel.birthDateProperty().set(myDate);
      }
      else
      {
        viewModel.birthDateProperty().set(null);
      }
    });

    viewModel.birthDateProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        try
        {
          LocalDate localDate = LocalDate.of(newValue.getYear(), newValue.getMonth(), newValue.getDay());
          birthDateInput.setValue(localDate);
        }
        catch (Exception e)
        {
          System.err.println(
              "Error updating DatePicker: " + e.getMessage() + " - values: day=" + newValue.getDay() + ", month="
                  + newValue.getMonth() + ", year=" + newValue.getYear());
          System.err.println("MyDate object: " + newValue);
        }
      }
      else
      {
        birthDateInput.setValue(null);
      }
    });
  }

  @FXML public void onRegister(ActionEvent e)
  {
    if (e.getSource() == buttonRegister)
    {
      viewModel.registerUser();
    }
  }

  @FXML public void onLogin(ActionEvent e)
  {
    if (e.getSource() == buttonLogin)
      ViewHandler.showView(ViewHandler.ViewType.LOGIN);
  }
}