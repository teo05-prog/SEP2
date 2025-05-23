package model.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a traveller (customer) in the train system.
 * This class extends the User class and adds traveller-specific functionality
 * including birthdate tracking and ticket management. Travellers can purchase
 * and manage multiple tickets for their journeys.
 *
 * @author Jan Lewek, Yelyzaveta Tkachenko
 */
public class Traveller extends User
{
  private MyDate birthDate;
  private final List<Ticket> tickets;

  /**
   * Constructs a new Traveller with the specified details.
   * The traveller starts with an empty list of tickets.
   *
   * @param name      the traveller's full name
   * @param email     the traveller's email address
   * @param password  the traveller's password
   * @param birthDate the traveller's birthdate
   */
  public Traveller(String name, String email, String password, MyDate birthDate)
  {
    super(name, email, password);
    this.birthDate = birthDate;
    tickets = new ArrayList<>();
  }

  /**
   * Adds a ticket to the traveller's collection.
   * This method simulates the purchase of a ticket by adding it to the traveller's ticket list.
   *
   * @param ticket the ticket to add to the traveller's collection
   */
  public void buyTicket(Ticket ticket)
  {
    tickets.add(ticket);
  }

  /**
   * Gets the list of all tickets owned by this traveller.
   * Returns a direct reference to the internal list, allowing external modification.
   *
   * @return the list of tickets owned by this traveller
   */
  public List<Ticket> getTickets()
  {
    return tickets;
  }

  /**
   * Gets the traveller's birthdate.
   *
   * @return the birthdate of the traveller
   */
  public MyDate getBirthDate()
  {
    return birthDate;
  }

  /**
   * Sets the traveller's birthdate.
   *
   * @param birthDate the new birthdate for the traveller
   */
  public void setBirthDate(MyDate birthDate)
  {
    this.birthDate = birthDate;
  }

  /**
   * Gets the traveller's name.
   *
   * @return the name of the traveller
   */
  public String getName()
  {
    return super.getName();
  }

  /**
   * Sets the traveller's name.
   *
   * @param name the new name for the traveller
   */
  public void setName(String name)
  {
    super.setName(name);
  }

  /**
   * Gets the traveller's password.
   *
   * @return the password of the traveller
   */
  public String getPassword()
  {
    return super.getPassword();
  }

  /**
   * Sets the traveller's password.
   *
   * @param password the new password for the traveller
   */
  public void setPassword(String password)
  {
    super.setPassword(password);
  }

  /**
   * Gets the traveller's email address.
   *
   * @return the email address of the traveller
   */
  public String getEmail()
  {
    return super.getEmail();
  }

  /**
   * Sets the traveller's email address.
   *
   * @param email the new email address for the traveller
   */
  public void setEmail(String email)
  {
    super.setEmail(email);
  }

  /**
   * Returns a string representation of the Traveller object.
   * The format includes "Traveller - " followed by the parent User's toString() method,
   * plus birthdate and ticket information.
   *
   * @return a comprehensive string representation of this traveller
   */
  public String toString()
  {
    return "Traveller - " + super.toString() + ", Birth Date: " + birthDate.toString() + ", Tickets: "
        + tickets.toString();
  }

  /**
   * Compares this Traveller with the specified object for equality.
   * Two Traveller objects are considered equal if they have the same properties
   * as defined by the parent User class's equals method and the same birthdate.
   *
   * @param obj the object to compare with this Traveller
   * @return true if the specified object is equal to this Traveller, false otherwise
   */
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