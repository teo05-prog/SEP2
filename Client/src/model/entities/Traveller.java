package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Traveller extends User
{
  private static final boolean isAdmin = false;
  private List<Ticket> tickets;

  public Traveller(String name, String password, String email, MyDate birthDate)
  {
    super(name, password, email, birthDate);
    tickets = new ArrayList<>();
  }

  public static boolean isAdmin()
  {
    return isAdmin;
  }

  public void buyTicket(Ticket ticket)
  {
    tickets.add(ticket);
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

  public MyDate getBirthDate()
  {
    return super.getBirthDate();
  }

  public void setBirthDate(MyDate birthDate)
  {
    super.setBirthDate(birthDate);
  }

  public String toString()
  {
    return "Traveller " + super.toString();
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Traveller traveller = (Traveller) obj;
    return isAdmin == Traveller.isAdmin && super.equals(obj);
  }
}