package model.entities;

import java.util.ArrayList;

public class Carriage
{
    private int carriageId;
    private final int seatCount = 16;
    private ArrayList<Seat> seats;

    public Carriage(int carriageId) {
        this.carriageId = carriageId;
        this.seats = new ArrayList<>(seatCount);
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



}
