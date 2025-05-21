package view.traveller.seat;

import com.google.gson.Gson;
import dtos.TrainDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.entities.*;
import network.ClientSocket;
import session.Session;
import view.ViewHandler;
import viewmodel.SearchTicketVM;
import viewmodel.SeatSelectionVM;

import java.util.List;
import java.util.Random;

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

  private SearchTicketVM searchTicketVM;

  public SeatSelectionController()
  {
    this.viewModel = new SeatSelectionVM();
  }

  public void init(SeatSelectionVM viewModel, SearchTicketVM searchTicketVM)
  {
    if (viewModel != null)
      this.viewModel = viewModel;
    this.searchTicketVM = searchTicketVM;
    this.viewModel.loadBookedSeatsForSelectedTrain();
    bindProperties();
  }

  public void initialize()
  {
    //    if (viewModel == null)
    //    {
    //      viewModel = new SeatSelectionVM();
    //    }
    //    bindProperties();
  }

  private void bindProperties()
  {
    // collect all seat buttons into a list to avoid repeating code
    allSeatButtons = List.of(seatNumber1, seatNumber2, seatNumber3, seatNumber4,
        seatNumber5, seatNumber6, seatNumber7, seatNumber8, seatNumber9,
        seatNumber10, seatNumber11, seatNumber12, seatNumber13, seatNumber14,
        seatNumber15, seatNumber16, bicycleSeatNumber17, bicycleSeatNumber18);

    // for each button assign logic for click
    for (int i = 0; i < allSeatButtons.size(); i++)
    {
      int seatNumber = i + 1;
      Button button = allSeatButtons.get(i);
      setupSeatButton(button, seatNumber);
    }

    continueButton.setOnAction(e -> {
//      if (viewModel.getSelectedSeats().isEmpty())
//      {
//        showAlert("No seat selected",
//            "Please select at least one seat before continuing.");
//        return;
//      }


      //prepare booking
      TrainDTO trainDTO = Session.getInstance().getSelectedTrainDTO();
      String email = Session.getInstance().getUserEmail();
      int ticketID = new Random().nextInt(1000000);

      Schedule schedule = new Schedule(trainDTO.scheduleId,new Station(trainDTO.from),new Station(trainDTO.to),trainDTO.departureDate,trainDTO.arrivalDate);

      Ticket ticket = new Ticket(ticketID, new Train(trainDTO.trainId),
          schedule, email);


      // set selected seat
      Integer seat = viewModel.getSelectedSeats().stream().findFirst().orElse(null);

      if (seat != null && seat >= 1 && seat <=16){
        ticket.setSeatId(new Seat(seat));
        System.out.println("Set seat on ticket: " + seat);
      }else if(seat != null && seat >=17 && seat<=18){
        ticket.setBicycleSeat(new Bicycle(seat));
      }
      System.out.println("Ticket before saving to session: "+ new Gson().toJson(ticket));
      //save for confirmation page
      Session.getInstance().setCurrentTicket(ticket);
      viewModel.confirmBooking(); //finalize in ViewModel
      //send to server
      try{
        if (ticket.getSeatId() !=null && ticket.getBicycleSeat() !=null){
          System.out.println("Booking ticket: "+new Gson().toJson(ticket));
          ClientSocket.sendRequest("ticket","createSeatAndBicycleTicket",ticket);
        }
        else if (ticket.getSeatId() != null)
        {
          ClientSocket.sendRequest("ticket","createSeatTicket", ticket);
        }
        else if (ticket.getBicycleSeat() !=null)
        {
          ClientSocket.sendRequest("ticket","createBicycleTicket", ticket);
        }else {
          ClientSocket.sendRequest("ticket","createTicket", ticket);
        }
        Session.getInstance().setCurrentTicket(ticket);
        System.out.println("Booking ticket: "+ new Gson().toJson(ticket));


        ViewHandler.showView(ViewHandler.ViewType.CONFIRM_TICKET);
      }catch (Exception ex){
        ex.printStackTrace();
        showAlert("Booking failed", "Could not complete booking: "+ex.getMessage());
      }
      refreshSeatButtons();
    });
  }

  private void setupSeatButton(Button button, int seatNumber)
  {
    if (viewModel.isSeatBooked(seatNumber))
    {
      button.setDisable(true);
      return;
    }

    boolean isSeat = seatNumber >= 1 && seatNumber <= 16;
    boolean isBicycle = seatNumber >= 17 && seatNumber <= 18;

    // restrict based on what user chose
    boolean seatAllowed = searchTicketVM.seatProperty().get();
    boolean bicycleAllowed = searchTicketVM.bicycleProperty().get();

    if ((isSeat && !seatAllowed) || (isBicycle && !bicycleAllowed))
    {
      button.setDisable(true); //not allowed based on traveller choice
      return;
    }

    // click toggles selection if seat is available
    button.setOnAction(e -> {
      viewModel.toggleSeatSelection(seatNumber);
      refreshSeatButtons();
    });

    updateSeatStyle(button, seatNumber);
  }

  private void updateSeatStyle(Button button, int seatNumber)
  {
    if (viewModel.isSeatBooked(seatNumber))
    {
      // booked
      button.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");
      button.setDisable(true);
    }
    else if (viewModel.getSelectedSeats().contains(seatNumber))
    {
      //selected
      button.setStyle(" -fx-background-color: #a72608; -fx-text-fill: white;");
    }
    else
    {
      // available
      button.setStyle(
          "-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2px;");
    }
  }

  private void refreshSeatButtons()
  {
    for (int i = 0; i < allSeatButtons.size(); i++)
    {
      updateSeatStyle(allSeatButtons.get(i), i + 1);
    }
  }

  private void showAlert(String title, String message)
  {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
