package network.requestHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import services.seat.SeatService;
import utilities.LogLevel;
import utilities.Logger;

import java.util.List;

public class SeatRequestHandler implements RequestHandler
{
  private final Gson gson = new GsonBuilder().create();
  private final SeatService seatService;
  private final Logger logger;

  public SeatRequestHandler(SeatService seatService, Logger logger)
  {
    this.seatService = seatService;
    this.logger = logger;
  }

  @Override public Object handler(String action, Object payload)
  {
    return switch (action)
    {
      case "getBookedSeats" -> handleGetBookedSeats(payload);
      default -> throw new IllegalArgumentException("Unknown action: " + action);
    };
  }

  private Object handleGetBookedSeats(Object payload)
  {
    try
    {
      int trainId = gson.fromJson(gson.toJson(payload), Double.class).intValue();
      List<Integer> bookedSeats = seatService.getBookedSeatsForTrain(trainId);

      logger.log("Returned booked seats for train ID: " + trainId, LogLevel.INFO);
      return bookedSeats;
    }
    catch (Exception e)
    {
      logger.log("Failed to fetch booked seats: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to fetch booked seats", e);
    }
  }
}
