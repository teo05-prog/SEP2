package view.traveller.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import view.ViewHandler;
import viewmodel.SearchTicketVM;

public class SearchTicketViewController
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

  public SearchTicketViewController()
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
  }

  @FXML public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new SearchTicketVM();
    }
  }

  private void setupUI()
  {
    startButton.setDisable(true);
    searchButton.disableProperty().bind(viewModel.inputValidProperty().not());
    ObservableList<String> stations = FXCollections.observableArrayList("Copenhagen", "Aarhus", "Odense", "Aalborg",
        "Esbjerg", "Randers", "Horsens", "Vejle", "Kolding", "Silkeborg", "Herning");
    fromComboBox.setItems(stations);
    toComboBox.setItems(stations);
    viewModel.fromProperty().bindBidirectional(fromComboBox.valueProperty());
    viewModel.toProperty().bindBidirectional(toComboBox.valueProperty());

    ObservableList<String> times = FXCollections.observableArrayList();
    for (int hour = 6; hour <= 22; hour++)
    {
      times.add(String.format("%02d:00", hour));
      times.add(String.format("%02d:30", hour));
    }
    timeComboBox.setItems(times);
    viewModel.timeProperty().bindBidirectional(timeComboBox.valueProperty());
    dateInput.valueProperty().addListener((obs, oldDate, newDate) -> {
      viewModel.dateProperty().set(newDate);
    });

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
    // do nothing because we are already on this view
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

  @FXML private void onMyAccountButton(ActionEvent e)
  {
    if (e.getSource() == myAccountButton)
    {
      ViewHandler.showView(ViewHandler.ViewType.USER_ACCOUNT);
    }
  }
}
