package network.requestHandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.SearchFilterDTO;
import dtos.TrainDTO;
import exceptions.ValidationException;
import services.search.SearchService;
import utilities.LogLevel;
import utilities.Logger;

import java.util.List;

public class SearchRequestHandler implements RequestHandler
{
  private final Gson gson = new GsonBuilder().create();
  private final SearchService searchService;
  private final Logger logger;

  public SearchRequestHandler(SearchService searchService, Logger logger)
  {
    this.logger = logger;
    this.searchService = searchService;
  }

  public Object handler(String action, Object payload)
  {
    return switch (action)
    {
      case "storeFilter" -> handleStoreFilter(payload);
      case "getFilteredTrains" -> handleGetFilteredTrains(payload);
      default -> throw new IllegalArgumentException("Unknown action: " + action);
    };
  }

  private Object handleStoreFilter(Object payload)
  {
    try
    {
      SearchFilterDTO filterDTO = gson.fromJson(gson.toJson(payload), SearchFilterDTO.class);
      //validate required fields
      if (filterDTO.userEmail == null || filterDTO.userEmail.isBlank())
        throw new ValidationException("User email is missing");

      searchService.saveSearchFilter(filterDTO);

      logger.log("Stored search filter for user: " + filterDTO.userEmail, LogLevel.INFO);
      return "Search filter stored successfully";
    }
    catch (ValidationException e)
    {
      logger.log("Validation failed: " + e.getMessage(), LogLevel.INFO);
      throw e;
    }
    catch (Exception e)
    {
      logger.log("Failed to store search filter: " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Failed to store search filter", e);
    }
  }

  private Object handleGetFilteredTrains(Object payload)
  {
    String email = gson.fromJson(gson.toJson(payload), String.class);
    List<TrainDTO> trains = searchService.getFilteredTrainsForUser(email);
    return trains;
  }
}
