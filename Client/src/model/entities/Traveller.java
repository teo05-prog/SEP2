package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Traveller extends User
{
  private MyDate birthDate;
  private List<Ticket> tickets;

  public Traveller(String name, String password, String email, MyDate birthDate)
  {
    super(name, password, email, registerRequest.getBirthday());
    this.birthDate = birthDate;
    tickets = new ArrayList<>();
  }

  public void buyTicket(Ticket ticket)
  {
    tickets.add(ticket);
  }

  public List<Ticket> getTickets()
  {
    return tickets;
  }

  public MyDate getBirthDate()
  {
    return birthDate;
  }

  public void setBirthDate(MyDate birthDate)
  {
    this.birthDate = birthDate;
  }

  public String getName()
  {
    return super.getName();
  }

  public void setName(String name)
  {
    super.setName(name);
  }

  public String getPassword()
  {
    return super.getPassword();
  }

  public void setPassword(String password)
  {
    super.setPassword(password);
  }

  public String getEmail()
  {
    return super.getEmail();
  }

  public void setEmail(String email)
  {
    super.setEmail(email);
  }

  public String toString()
  {
    return "Traveller - " + super.toString();
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Traveller traveller = (Traveller) obj;
    return birthDate.equals(traveller.birthDate) && super.equals(obj);
  }
}