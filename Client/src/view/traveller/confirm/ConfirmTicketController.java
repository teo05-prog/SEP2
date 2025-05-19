package view.traveller.confirm;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import model.entities.Ticket;
import session.Session;
import viewmodel.ConfirmTicketVM;

public class ConfirmTicketController
{
  private ConfirmTicketVM viewModel;

  @FXML private Label dateTimeLabel;
  @FXML private Label fromLabel;
  @FXML private Label toLabel;
  @FXML private Label seatLabel;
  @FXML private Label bicycleLabel;

  public void init(ConfirmTicketVM viewModel){
    this.viewModel = viewModel;
    bindData();
  }

  private void bindData(){
    Ticket ticket = Session.getInstance().getCurrentTicket();
    if (ticket == null) return;

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
    System.out.println("Ticket in confirm page: "+new Gson().toJson(ticket));
  }
}
