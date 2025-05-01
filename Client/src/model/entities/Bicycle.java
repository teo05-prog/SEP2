package model.entities;

public class Bicycle
{
    private int bicycleId;
    private boolean isReserved;

    public Bicycle(int bicycleId) {
        this.bicycleId = bicycleId;
        this.isReserved = false;
    }

    public int getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(int bicycleId) {
        this.bicycleId = bicycleId;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved() {
        isReserved = true;
    }

    @Override
    public String toString() {
        return "Bicycle ID: " + bicycleId + ", Reserved: " + isReserved;
    }
}
