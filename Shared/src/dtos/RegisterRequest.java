package dtos;

public class RegisterRequest
{
  private String name;
  private String email;
  private String password;
  private String repeatPassword;
  private String birthday;

  public RegisterRequest(){
    //no-args constructor for Gson
  }

  public RegisterRequest(String name, String email, String password, String repeatPassword, String birthday){
    this.name = name;
    this.email = email;
    this.password = password;
    this.repeatPassword = repeatPassword;
    this.birthday = birthday;
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

  public String getRepeatPassword()
  {
    return repeatPassword;
  }

  public String getBirthday()
  {
    return birthday;
  }
}
