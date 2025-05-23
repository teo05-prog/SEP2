package model.entities;

/**
 * Represents a base user in the train system.
 * This is a representation of a user that serves as the parent class
 * for specific user types such as Admin and Traveller. It contains common
 * user properties and functionality shared across all user types.
 * The class provides basic user management functionality including
 * authentication credentials and user identification.
 *
 * @author Yelyzaveta Tkachenko, Bianca Buzdugan
 */
public class User
{
  private String name;
  private String password;
  private String email;

  /**
   * Constructs a new User with the specified credentials.
   *
   * @param name     the user's full name
   * @param email    the user's email address
   * @param password the user's password
   */
  public User(String name, String email, String password)
  {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  /**
   * Checks if this user is an administrator.
   * This method uses instanceof to determine if the current user object
   * is an instance of the Admin class.
   *
   * @return true if the user is an Admin, false otherwise
   */
  public boolean isAdmin()
  {
    return this instanceof Admin;
  }

  /**
   * Gets the user's name.
   *
   * @return the name of the user
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the user's name.
   *
   * @param name the new name for the user
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Gets the user's password.
   *
   * @return the password of the user
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Sets the user's password.
   *
   * @param password the new password for the user
   */
  public void setPassword(String password)
  {
    this.password = password;
  }

  /**
   * Gets the user's email address.
   *
   * @return the email address of the user
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * Sets the user's email address.
   *
   * @param email the new email address for the user
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * Returns a string representation of the User object.
   * The format includes the user's name, password, and email address.
   *
   * @return a string representation of this user showing name, password, and email
   */
  public String toString()
  {
    return "Name: " + name + ", Password: " + password + ", Email: " + email;
  }

  /**
   * Compares this User with the specified object for equality.
   * Two User objects are considered equal if they have the same name, password, and email.
   *
   * @param obj the object to compare with this User
   * @return true if the specified object is equal to this User, false otherwise
   */
  @Override public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
      return false;

    User user = (User) obj;
    return name.equals(user.name) && password.equals(user.password) && email.equals(user.email);
  }
}