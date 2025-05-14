package persistance.search;

import dtos.SearchFilterDTO;
import dtos.TrainDTO;
import model.entities.Train;

import java.util.List;

public interface SearchDAO {

  void saveFilter(SearchFilterDTO filterDTO);
  List<TrainDTO> getFilteredTrainsForUser(String email);
}
