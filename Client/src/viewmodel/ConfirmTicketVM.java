package viewmodel;

import model.entities.MyDate;
import model.entities.Ticket;
import session.Session;

public class ConfirmTicketVM
{
  public Ticket getCurrentTicket()
  {
    return Session.getInstance().getCurrentTicket();
  }

  public String getFormattedDateTime()
  {
    Ticket ticket = getCurrentTicket();
    if (ticket == null)
      return "";

    MyDate dep = ticket.getScheduleId().getDepartureDate();
    return String.format("%02d/%02d/%04d %02d:%02d", dep.getDay(), dep.getMonth(), dep.getYear(), dep.getHour(),
        dep.getMinute());
  }
}
