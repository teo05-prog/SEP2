package viewmodel;

import model.entities.Traveller;

public class MyAccountVM
{
  private Traveller traveller;

  public String getName(){
    return traveller.getName();
  }

  public String getBirthday(){
    return traveller.getBirthDate().toString();
  }

  public String getEmail(){
    return traveller.getEmail();
  }
}
