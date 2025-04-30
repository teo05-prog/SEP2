package model.entities;

public class Carriage
{
    private int carriageId;
    private final int seat =16;
    private final int bicycle=2;

    public Carriage(int carriageId) {
        this.carriageId = carriageId;
    }

    public int getCarriageId() {
        return carriageId;
    }

    public void setCarriageId(int carriageId) {
        this.carriageId = carriageId;
    }

    public int getSeat() {
        return seat;
    }

    public int getBicycle() {
        return bicycle;
    }

}
