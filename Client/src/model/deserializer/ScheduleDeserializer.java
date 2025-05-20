package model.deserializer;

import com.google.gson.*;
import model.entities.MyDate;
import model.entities.Schedule;
import model.entities.Station;

import java.lang.reflect.Type;

public class ScheduleDeserializer implements JsonDeserializer<Schedule>
{
  @Override public Schedule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException
  {
    JsonObject jsonObject = json.getAsJsonObject();

    int scheduleId = jsonObject.get("scheduleId").getAsInt();

    Station departureStation = context.deserialize(jsonObject.get("departureStation"), Station.class);

    Station arrivalStation = context.deserialize(jsonObject.get("arrivalStation"), Station.class);

    MyDate departureDate = context.deserialize(jsonObject.get("departureDate"), MyDate.class);

    MyDate arrivalDate = context.deserialize(jsonObject.get("arrivalDate"), MyDate.class);

    return new Schedule(scheduleId, departureStation, arrivalStation, departureDate, arrivalDate);
  }
}
