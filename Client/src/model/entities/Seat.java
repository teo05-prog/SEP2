package model.entities;

public class Seat {
    private int seatId;
    private boolean isReserved;


    public Seat(int seatId) {
        this.seatId = seatId;
        this.isReserved = false;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved() {
        isReserved = true;
    }


    @Override
    public String toString() {
        return "Seat ID: " + seatId + ", Reserved: " + isReserved  ;
    }
}
