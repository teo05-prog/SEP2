package model.entities;

public class Admin extends User
{
  private boolean isAdmin;

  public Admin(String name, String password, String email)
  {
    super(name, password, email);
    this.isAdmin = true;
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

  public boolean isAdmin()
  {
    return isAdmin;
  }

  public void setAdmin(boolean isAdmin)
  {
    this.isAdmin = isAdmin;
  }

  public String toString()
  {
    return "Admin - " + super.toString();
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    return super.equals(obj);
  }
}