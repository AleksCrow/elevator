package generation;

import model.Cargo;

import java.util.List;
import java.util.Map;

public interface Populator {

    Map<Integer, List<Cargo>> getBuildingPopulation();

    int getBuildingFloorsCount();


}
