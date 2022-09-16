package generation;

import model.Cargo;
import model.Person;

import java.util.*;

public class RandomPopulator implements Populator {

    private static final int MIN_FLOORS = 5;
    private static final int MAX_FLOORS = 20;
    private static final int MIN_PERSONS_ON_FLOOR = 0;
    private static final int MAX_PERSONS_ON_FLOOR = 10;

    private final int buildingFloorsCount;
    private Map<Integer, List<Cargo>> buildingPopulation;

    public RandomPopulator() {
        this.buildingFloorsCount = generateFloors();
        populateFloors();
    }

    private int generateFloors() {
        int r = new Random().nextInt(MAX_FLOORS - MIN_FLOORS + 1) + MIN_FLOORS;
        return r;
    }

    private Cargo personGenerator(int startFloor) {
        Cargo person = new Person(startFloor, new Random().nextInt(buildingFloorsCount) + 1);
        while (startFloor == person.getTargetFloor()) {
            person.setTargetFloor(new Random().nextInt(buildingFloorsCount) + 1);
        }
        return person;
    }

    private void populateFloors() {
        buildingPopulation = new HashMap<>();
        for (int i = 1; i <= buildingFloorsCount; i++) {
            List<Cargo> personsOnFloor = new ArrayList<>();
            int personsCount = new Random().nextInt(MAX_PERSONS_ON_FLOOR);
            for (int j = 0; j < personsCount; j++) {
                personsOnFloor.add(personGenerator(i));
            }
            buildingPopulation.put(i, personsOnFloor);
        }
    }

    public Map<Integer, List<Cargo>> getBuildingPopulation() {
        return buildingPopulation;
    }

    public int getBuildingFloorsCount() {
        return buildingFloorsCount;
    }
}
