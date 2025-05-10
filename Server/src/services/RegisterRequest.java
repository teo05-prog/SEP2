package services;

import model.entities.MyDate;

public class RegisterRequest
{
  private String name;
  private String email;
  private String password;
  private MyDate birthDate;

  public RegisterRequest()
  {
  }

  public RegisterRequest(String name, String email, String password, MyDate birthDate)
  {
    this.name = name;
    this.email = email;
    this.password = password;
    this.birthDate = birthDate;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public MyDate getBirthDate()
  {
    return birthDate;
  }

  public void setBirthDate(MyDate birthDate)
  {
    this.birthDate = birthDate;
  }
}
