package model.entities;

/**
 * Represents an administrator user in the system.
 * This class extends the User class and provides administrative functionality.
 * Admins can change information about trains.
 *
 * @author Jan Lewek
 */
public class Admin extends User
{
  /**
   * Constructs a new Admin with the specified name, email, and password.
   *
   * @param name     the administrator's full name
   * @param email    the administrator's email address
   * @param password the administrator's password
   */
  public Admin(String name, String email, String password)
  {
    super(name, email, password);
  }

  /**
   * Gets the administrator's name.
   *
   * @return the name of the administrator
   */
  public String getName()
  {
    return super.getName();
  }

  /**
   * Sets the administrator's name.
   *
   * @param name the new name for the administrator
   */
  public void setName(String name)
  {
    super.setName(name);
  }

  /**
   * Gets the administrator's password.
   *
   * @return the password of the administrator
   */
  public String getPassword()
  {
    return super.getPassword();
  }

  /**
   * Sets the administrator's password.
   *
   * @param password the new password for the administrator
   */
  public void setPassword(String password)
  {
    super.setPassword(password);
  }

  /**
   * Gets the administrator's email address.
   *
   * @return the email address of the administrator
   */
  public String getEmail()
  {
    return super.getEmail();
  }

  /**
   * Sets the administrator's email address.
   *
   * @param email the new email address for the administrator
   */
  public void setEmail(String email)
  {
    super.setEmail(email);
  }

  /**
   * Returns a string representation of the Admin object.
   * The format is "Admin - " followed by the parent User's toString() method.
   *
   * @return a string representation of this Admin
   */
  public String toString()
  {
    return "Admin - " + super.toString();
  }

  /**
   * Compares this Admin with the specified object for equality.
   * Two Admin objects are considered equal if they have the same properties,
   * as defined by the parent User class's equals method.
   *
   * @param obj the object to compare with this Admin
   * @return true if the specified object is equal to this Admin, false otherwise
   */
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    return super.equals(obj);
  }
}