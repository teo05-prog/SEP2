package view.traveller.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.ViewHandler;
import viewmodel.SearchTicketVM;

public class SearchTicketController
{
  private SearchTicketVM viewModel;

  @FXML private Button startButton;
  @FXML private Button previousButton;
  @FXML private Button upcomingButton;
  @FXML private Button myAccountButton;
  @FXML private Label messageLabel;
  @FXML private ComboBox<String> fromComboBox;
  @FXML private ComboBox<String> toComboBox;
  @FXML private DatePicker dateInput;
  @FXML private ComboBox<String> timeComboBox;
  @FXML private CheckBox seatCheckBox;
  @FXML private CheckBox bicycleCheckBox;
  @FXML private Button searchButton;

  public SearchTicketController()
  {
    this.viewModel = new SearchTicketVM();
  }

  public void init(SearchTicketVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
    }
    setupUI();
    // No need to call setupUI() here as it's already called in initialize()
  }

  @FXML public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new SearchTicketVM();
    }
    //    setupUI();
  }

  private void setupUI()
  {
    startButton.setDisable(true);
    // disable the button until all required fields are filled
    searchButton.disableProperty().bind(viewModel.inputValidProperty().not());
    // should be changed
    ObservableList<String> stations = FXCollections.observableArrayList("Copenhagen", "Aarhus", "Odense", "Aalborg",
        "Esbjerg");
    fromComboBox.setItems(stations);
    toComboBox.setItems(stations);
    // bind from and to stations
    viewModel.fromProperty().bindBidirectional(fromComboBox.valueProperty());
    viewModel.toProperty().bindBidirectional(toComboBox.valueProperty());

    ObservableList<String> times = FXCollections.observableArrayList();
    for (int hour = 6; hour <= 22; hour++)
    {
      times.add(String.format("%02d:00", hour));
      times.add(String.format("%02d:30", hour)); // Fixed: This was "%02d:00" twice
    }
    timeComboBox.setItems(times);
    //bind time
    viewModel.timeProperty().bindBidirectional(timeComboBox.valueProperty());

    dateInput.setValue(java.time.LocalDate.now());
    // bind date
    viewModel.dateProperty().bindBidirectional(dateInput.valueProperty());

    //bind seat and bicycle
    viewModel.seatProperty().bindBidirectional(seatCheckBox.selectedProperty());
    viewModel.bicycleProperty().bindBidirectional(bicycleCheckBox.selectedProperty());

    messageLabel.setText("");
    seatCheckBox.setSelected(false);
    bicycleCheckBox.setSelected(false);
  }

  @FXML private void onSearchButton()
  {
    messageLabel.setText(" ");
    viewModel.startTrainSearch();
  }

  @FXML private void onStartButton()
  {
    // do nothing because we are already on Start page
  }

  @FXML private void onPreviousButton()
  {
    //later
  }

  @FXML private void onUpcomingButton()
  {
    //later
  }

  @FXML private void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.USER_ACCOUNT);
    }
  }
}
