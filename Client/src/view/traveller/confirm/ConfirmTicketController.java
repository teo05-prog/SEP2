package view.traveller.confirm;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.entities.Ticket;
import session.Session;
import view.ViewHandler;
import viewmodel.ConfirmTicketVM;

public class ConfirmTicketController
{
  private ConfirmTicketVM viewModel;

  @FXML private Label dateTimeLabel;
  @FXML private Label fromLabel;
  @FXML private Label toLabel;
  @FXML private Label seatLabel;
  @FXML private Label bicycleLabel;
  @FXML private Label noReservationLabel;
  @FXML private Button buyButton;

  public void init(ConfirmTicketVM viewModel){
    this.viewModel = viewModel;
    bindData();
  }

  private void bindData(){
    Ticket ticket = Session.getInstance().getCurrentTicket();
    System.out.println("Ticket in confirm page: "+new Gson().toJson(ticket));
    if (ticket == null) {
      System.out.println("Ticket is null");
      return;
    }

    fromLabel.setText(ticket.getScheduleId().getDepartureStation().getName());
    toLabel.setText(ticket.getScheduleId().getArrivalStation().getName());

    dateTimeLabel.setText(viewModel.getFormattedDateTime());

    if (ticket.getSeatId() != null){
      seatLabel.setText("Seat: "+ticket.getSeatId().getSeatId());
    }else {
      seatLabel.setText("Seat: None");
    }

    if (ticket.getBicycleSeat() != null){
      bicycleLabel.setText("Bicycle: "+ticket.getBicycleSeat().getBicycleSeatId());
    }else {
      bicycleLabel.setText("Bicycle: None");
    }

    if (ticket.getSeatId() == null && ticket.getBicycleSeat() == null){
      noReservationLabel.setText("This ticket has no seat or bicycle reserved");
      noReservationLabel.setVisible(true);
    }else {
      noReservationLabel.setVisible(false);
    }

  }

  public void onBuyButton(ActionEvent actionEvent)
  {
    if (actionEvent.getSource()== buyButton){
      ViewHandler.showView(ViewHandler.ViewType.LOGGEDIN_USER);
    }
  }
}
