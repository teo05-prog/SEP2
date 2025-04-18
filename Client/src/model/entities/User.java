package model.entities;

public class User
{
    private String name;
    private String password;
    private String email;
    private MyDate birthDate;

    public User(String name, String password, String email, MyDate birthDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void Name(String name) {
      if (name == null)
      {throw new IllegalArgumentException("Name must not be null");}
      if (name.length() > 70)
      {throw new IllegalArgumentException("Name must be at most 70 characters");}
      if (!Character.isLetter(name.charAt(0)))
      {throw new IllegalArgumentException("Name must start with a letter");}
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

      if (password == null)
      {
        throw new IllegalArgumentException("Password must not be null");
      }
      if (password.length() < 8)
      {
        throw new IllegalArgumentException("Password must be at least 8 characters");
      }
      if (password.length() > 25)
      {
        throw new IllegalArgumentException("Password must be at most 20 characters");
      }


      this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
      if (email == null)
      {
        throw new IllegalArgumentException("Email must not be null");
      }
      if (!email.contains("@") || !email.contains("."))
      {
        throw new IllegalArgumentException("Email must contain '@' and '.'");
      }
      this.email = email;
    }
    public MyDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(MyDate birthDate) {
        this.birthDate = birthDate;
    }
    public String toString() {
        return "User is " +
                "username=" + name + '\n' +
                ", password=" + password + '\n' +
                ", email=" + email + "\n" +
                ", birthDate=" + birthDate + "\n";
    }

  @Override public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass()) return false;

    User user = (User) obj;
    return
      name.equals(user.getName()) &&
      password.equals(user.getPassword()) &&
      email.equals(user.getEmail()) &&
      birthDate.equals(user.getBirthDate());
  }
}
