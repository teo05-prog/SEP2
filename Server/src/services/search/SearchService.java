package services.search;

import dtos.SearchFilterDTO;
import dtos.TrainDTO;
import model.entities.Train;

import java.util.List;

public interface SearchService
{
  void saveSearchFilter(SearchFilterDTO filterDTO);
  List<TrainDTO> getFilteredTrainsForUser(String email);
}
