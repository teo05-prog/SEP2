package model.entities;

import java.util.ArrayList;

public class Train
{
  private int trainId;
  private ArrayList<Carriage> carriages;

  public Train(int trainId,ArrayList<Carriage> carriages) {
    this.trainId= trainId;
    this.carriages=new ArrayList<>();
  }
  public ArrayList<Carriage> getCarriages() {
    return carriages;
  }
  public void addCarriage(Carriage carriage) {
    this.carriages.add(carriage);
  }
  public int getTrainId() {
    return trainId;
  }
  public void setTrainId(int trainId) {
    this.trainId = trainId;
  }
  public void removeCarriage(Carriage carriage) {
    this.carriages.remove(carriage);
  }

public void removeCarriageById(int carriageId) {
    for (int i=0;i<carriages.size();i++){
        if (carriages.get(i).getCarriageId()==carriageId){
            carriages.remove(i);
        }
    }
}
  public String toString()
  {
    String string = "";
    string += "Train ID: " + trainId + "\n";
    for (int i=0;i<carriages.size();i++)
    {
      string += "Carriage ID: " + carriages.get(i).getCarriageId() + ", Seat: " + carriages.get(i).getSeats()+ "\n";
    }

    return string;
  }

}
