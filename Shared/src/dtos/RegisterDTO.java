package dtos;

import model.entities.MyDate;

public class RegisterDTO
{
  private String name;
  private String email;
  private String password;
  private MyDate birthday;

  public RegisterDTO()
  {
  }

  public RegisterDTO(String name, String email, String password, MyDate birthday)
  {
    this.name = name;
    this.email = email;
    this.password = password;
    this.birthday = birthday;
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

  public MyDate getBirthday()
  {
    return birthday;
  }

  public void setBirthday(MyDate birthday)
  {
    this.birthday = birthday;
  }
}
