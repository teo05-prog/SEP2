package model.entities;

import java.util.ArrayList;

public class Train
{
  private int trainId;
  private ArrayList<Carriage> carriages;
  private Station from;
  private Station to;

  public Train(int trainId,ArrayList<Carriage> carriages, Station from, Station to, MyDate departureDate, MyDate departureTime) {
    this.trainId= trainId;
    this.carriages=new ArrayList<>();
    this.from=from;
    this.to=to;
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
  public Station getFrom() {
    return from;
  }
  public void setFrom(Station from) {
    this.from = from;
  }
  public Station getTo() {
    return to;
  }
  public void setTo(Station to) {
    this.to = to;
  }

  public String toString()
  {
    String string = "";
    string += "Train ID: " + trainId + "\n";
    string += "From: " + from.getName() + "\n";
    string += "To: " + to.getName() + "\n";
    for (int i=0;i<carriages.size();i++)
    {
      string += "Carriage ID: " + carriages.get(i).getCarriageId() + ", Seat: " + carriages.get(i).getSeats()+ "\n";
    }

    return string;
  }

  public void remove()
  {
    // to be implemented with database
  }
}
