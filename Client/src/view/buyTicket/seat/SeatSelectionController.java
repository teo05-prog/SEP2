package view.buyTicket.seat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import viewmodel.SeatSelectionVM;

import java.util.List;

public class SeatSelectionController
{
  private SeatSelectionVM viewModel;
  private List<Button> allSeatButtons;

  @FXML private Button seatNumber7;
  @FXML private Button seatNumber3;
  @FXML private Button seatNumber5;
  @FXML private Button seatNumber8;
  @FXML private Button seatNumber2;
  @FXML private Button seatNumber6;
  @FXML private Button seatNumber4;
  @FXML private Button seatNumber9;
  @FXML private Button seatNumber15;
  @FXML private Button seatNumber11;
  @FXML private Button seatNumber13;
  @FXML private Button seatNumber16;
  @FXML private Button seatNumber10;
  @FXML private Button seatNumber14;
  @FXML private Button seatNumber12;
  @FXML private Button seatNumber1;

  @FXML private Button bicycleSeatNumber18;
  @FXML private Button bicycleSeatNumber17;

  @FXML private Button continueButton;

  public SeatSelectionController()
  {
    this.viewModel = new SeatSelectionVM();
  }

  public void init(SeatSelectionVM viewModel)
  {
    if (viewModel != null)
    {
      this.viewModel = viewModel;
      bindProperties();
    }
  }

  public void initialize()
  {
    if (viewModel == null)
    {
      viewModel = new SeatSelectionVM();
    }
    bindProperties();
  }

  private void bindProperties()
  {
    // collect all seat buttons into a list to avoid repeating code
    allSeatButtons = List.of(seatNumber1, seatNumber2, seatNumber3, seatNumber4,
        seatNumber5, seatNumber6, seatNumber7, seatNumber8, seatNumber9,
        seatNumber10, seatNumber11, seatNumber12, seatNumber13, seatNumber14,
        seatNumber15, seatNumber16, bicycleSeatNumber17, bicycleSeatNumber18);

    // for each button assign logic for click
    for(int i = 0; i< allSeatButtons.size(); i++){
      int seatNumber = i+1;
      Button button = allSeatButtons.get(i);
      setupSeatButton(button, seatNumber);
    }

    continueButton.setOnAction(e ->{
      // finalize selected seat
      viewModel.confirmBooking();
      // update UI to show booked
      refreshSeatButtons();
    });
  }

  private void setupSeatButton (Button button, int seatNumber){
    if (viewModel.isSeatBooked(seatNumber)){
      button.setDisable(true);
    }
    // click toggles selection if seat is available
    button.setOnAction(e ->{
      viewModel.toggleSeatSelection(seatNumber);
      updateSeatStyle(button, seatNumber);
    });

    updateSeatStyle(button, seatNumber);
  }

  private void updateSeatStyle(Button button, int seatNumber){
    if (viewModel.isSeatBooked(seatNumber)){
      // booked
      button.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");
      button.setDisable(true);
    }
    else if (viewModel.getSelectedSeats().contains(seatNumber))
    {
      //selected
      button.setStyle(" -fx-background-color: #a72608; -fx-text-fill: white;");
    }else {
      // available
      button.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2px;");
    }
  }

  private void refreshSeatButtons(){
    for (int i = 0; i < allSeatButtons.size(); i++){
      updateSeatStyle(allSeatButtons.get(i), i+1);
    }
  }

}
