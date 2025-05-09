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
    // Constructor injection of the view model
    this.viewModel = viewModel;
  }

  @FXML
  public void initialize()
  {
    // Initialize and bind all properties
    bindProperties();
  }

  private void bindProperties()
  {
    // View model is now always initialized in constructor, no need to check for null

    nameInput.textProperty().bindBidirectional(viewModel.nameProperty());
    emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
    passwordInput.textProperty().bindBidirectional(viewModel.passwordProperty());
    repeatPasswordInput.textProperty().bindBidirectional(viewModel.repeatPasswordProperty());

    messageLabel.textProperty().bind(viewModel.messageProperty());
    buttonRegister.disableProperty().bind(viewModel.enableRegisterButtonProperty());

    // Handle DatePicker with proper bidirectional conversion
    setupDatePickerBinding();

    // Listen for successful registration
    viewModel.registrationSucceededProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue)
      {
        ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
      }
    }));
  }

  private void setupDatePickerBinding()
  {
    // Initial value from ViewModel to DatePicker
    MyDate initialDate = viewModel.birthDateProperty().get();
    if (initialDate != null)
    {
      try {
        // Debug logging to verify values
        System.out.println("Initial date from VM - day: " + initialDate.getDay() +
            ", month: " + initialDate.getMonth() +
            ", year: " + initialDate.getYear());

        // Create LocalDate with correct field mapping
        LocalDate localDate = LocalDate.of(
            initialDate.getYear(),  // Year
            initialDate.getMonth(), // Month
            initialDate.getDay()    // Day
        );
        birthDateInput.setValue(localDate);
      } catch (Exception e) {
        System.err.println("Error converting initial date: " + e.getMessage() +
            " - values: day=" + initialDate.getDay() +
            ", month=" + initialDate.getMonth() +
            ", year=" + initialDate.getYear());
        System.err.println("MyDate object: " + initialDate);
      }
    }

    // When DatePicker changes, update the ViewModel
    birthDateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        int day = newValue.getDayOfMonth();
        int month = newValue.getMonthValue();
        int year = newValue.getYear();

        // Debug to verify correct values
        System.out.println("From DatePicker - day: " + day + ", month: " + month + ", year: " + year);

        // FIXED: Create MyDate with the correct parameter order
        // The second constructor is: MyDate(int year, int month, int day)
        MyDate myDate = new MyDate(year, month, day);

        // Debug to verify MyDate was created correctly
        System.out.println("Created MyDate: " + myDate);

        viewModel.birthDateProperty().set(myDate);
      }
      else
      {
        viewModel.birthDateProperty().set(null);
      }
    });

    // When ViewModel birthDate changes, update the DatePicker
    viewModel.birthDateProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null)
      {
        try {
          // Debug logging to identify the issue
          System.out.println("From VM - day: " + newValue.getDay() +
              ", month: " + newValue.getMonth() +
              ", year: " + newValue.getYear());

          // Create a LocalDate ensuring values are within range
          LocalDate localDate = LocalDate.of(
              newValue.getYear(),  // Use the year as year
              newValue.getMonth(), // Use the month as month
              newValue.getDay()    // Use the day as day
          );
          birthDateInput.setValue(localDate);
          System.out.println("Successfully converted using standard order: " + localDate);
        } catch (Exception e) {
          System.err.println("Error updating DatePicker: " + e.getMessage() +
              " - values: day=" + newValue.getDay() +
              ", month=" + newValue.getMonth() +
              ", year=" + newValue.getYear());
          System.err.println("MyDate object: " + newValue);
        }
      }
      else
      {
        birthDateInput.setValue(null);
      }
    });
  }

  @FXML
  public void onRegister(ActionEvent e)
  {
    if (e.getSource() == buttonRegister) {
      // Verify date values before registration
      MyDate date = viewModel.birthDateProperty().get();
      if (date != null) {
        System.out.println("Before registration - day: " + date.getDay() +
            ", month: " + date.getMonth() +
            ", year: " + date.getYear());
      }

      viewModel.registerUser();
    }
  }

  @FXML
  public void onLogin(ActionEvent e)
  {
    if (e.getSource() == buttonLogin)
      ViewHandler.showView(ViewHandler.ViewType.LOGIN);
  }
}