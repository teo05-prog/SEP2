package dtos;

public class AddScheduleDTO
{
    private final String scheduleId;
    private final String departureStation;
    private final String arrivalStation;
    private final String departureDate;
    private final String departureTime;
    private final String arrivalDate;
    private final String arrivalTime;

    public AddScheduleDTO(String scheduleId, String departureStation, String arrivalStation, String departureDate, String departureTime, String arrivalDate, String arrivalTime) {
        this.scheduleId = scheduleId;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
}
