package model.entities;

public class Admin extends User
{
  private static final boolean isAdmin = true;

  public Admin(String name, String password, String email, MyDate birthDate)
  {
    super(name, password, email, birthDate);
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
    return "Admin " + super.toString();
  }

  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Admin admin = (Admin) obj;
    return isAdmin == Admin.isAdmin && super.equals(obj);
  }

  public boolean isAdmin()
  {
    return Admin.isAdmin;
  }
}