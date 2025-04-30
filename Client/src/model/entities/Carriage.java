package model.entities;

import java.util.ArrayList;

public class Carriage
{
    private int carriageId;
  private ArrayList<Seat> seats;
  private ArrayList<Bicycle> bicycles;

    public Carriage(int carriageId) {
        this.carriageId = carriageId;
      int seatCount = 16;
      this.seats = new ArrayList<>(seatCount);
      int bicycleCount = 2;
      this.bicycles = new ArrayList<>(bicycleCount);
    }
    public int getCarriageId() {
        return carriageId;
    }
    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }
    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }
    public ArrayList<Seat> getSeats() {
        return seats;
    }
    public void removeSeat(Seat seat) {
        this.seats.remove(seat);
    }
    public void removeSeatById(int seatId) {
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getSeatId() == seatId) {
                seats.remove(i);
            }
        }
    }
    public void addBicycle(Bicycle bicycle) {
        this.bicycles.add(bicycle);
    }
    public ArrayList<Bicycle> getBicycles() {
        return bicycles;
    }
    public void removeBicycle(Bicycle bicycle) {
        this.bicycles.remove(bicycle);
    }
    public void removeBicycleById(int bicycleId) {
        for (int i = 0; i < bicycles.size(); i++) {
            if (bicycles.get(i).getBicycleId() == bicycleId) {
                bicycles.remove(i);
            }
        }
    }
    public String toString() {
      String str="";
      str += "Carriage ID: " + carriageId + "\n";
      for (int i = 0; i < seats.size(); i++) {
          str += "Seat ID: " + seats.get(i).getSeatId() + ", Reserved: " + seats.get(i).isReserved() + "\n";
      }
      for (int i = 0; i < bicycles.size(); i++) {
          str += "Bicycle ID: " + bicycles.get(i).getBicycleId() + ", Reserved: " + bicycles.get(i).isReserved() + "\n";
      }
      return str;
    }


}
