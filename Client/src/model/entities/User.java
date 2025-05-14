package model.entities;

public class User
{
  private String name;
  private String password;
  private String email;

  public User(String name, String email, String password)
  {
    this.name = name;
    this.password = password;
    this.email = email;
  }

  public boolean isAdmin()
  {
    return this instanceof Admin;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String toString()
  {
    return "Name: " + name + ", Password: " + password + ", Email: " + email;
  }

  @Override public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
      return false;

    User user = (User) obj;
    return name.equals(user.name) && password.equals(user.password) && email.equals(user.email);
  }
}