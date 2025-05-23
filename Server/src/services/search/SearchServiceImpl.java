package services.search;

import dtos.SearchFilterDTO;
import dtos.TrainDTO;
import persistance.search.SearchDAO;
import utilities.LogLevel;
import utilities.Logger;

import java.util.Arrays;
import java.util.List;

public class SearchServiceImpl implements SearchService
{
  private final SearchDAO searchDAO;
  private Logger logger;

  public SearchServiceImpl(SearchDAO searchDAO, Logger logger)
  {
    this.searchDAO = searchDAO;
    this.logger = logger;
  }

  @Override public void saveSearchFilter(SearchFilterDTO filterDTO)
  {
    try
    {
      searchDAO.saveFilter(filterDTO);
      logger.log("Search filter saved via service for user: " + filterDTO.userEmail, LogLevel.INFO);
    }
    catch (Exception e)
    {
      logger.log("Service failed to save filter: " + Arrays.toString(e.getStackTrace()), LogLevel.ERROR);
      throw new RuntimeException("Service error: failed to save search filter", e);
    }
  }

  @Override public List<TrainDTO> getFilteredTrainsForUser(String email)
  {
    try
    {
      return searchDAO.getFilteredTrainsForUser(email);
    }
    catch (Exception e)
    {
      logger.log("Error retrieving trains for " + email + ": " + e.getMessage(), LogLevel.ERROR);
      throw new RuntimeException("Could not load filtered trains", e);
    }
  }
}
