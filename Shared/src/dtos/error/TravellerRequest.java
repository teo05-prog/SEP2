package dtos.error;

import model.entities.MyDate;

import java.io.Serializable;

public record TravellerRequest(String name, String email, String password, MyDate birthDate) implements Serializable
{
  @Override public String toString()
  {
    return "UserRequest{" + "name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + '}'
        + ", birthDate='" + birthDate + '\'' + '}';
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public MyDate getBirthDate()
  {
    return birthDate;
  }
}
