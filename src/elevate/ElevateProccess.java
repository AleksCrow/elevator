package elevate;

import generation.Populator;
import model.*;

import java.util.*;

public class ElevateProccess implements Elevate {
    private final int buildingFloorsCount;
    private Map<Integer, List<Cargo>> floorsPopulation;
    private final Elevator elevator;
    private final List<Step> stepList;

    public static int ITERATIONS_COUNT;
    private static final String ELEVATOR_DIRECTION_UP = "^";
    private static final String ELEVATOR_DIRECTION_DOWN = "v";

    public ElevateProccess(Populator populator, Elevator elevator) {
        this.buildingFloorsCount = populator.getBuildingFloorsCount();
        this.floorsPopulation = populator.getBuildingPopulation();
        this.stepList = new ArrayList<>();
        this.elevator = elevator;
    }

    public void transport() {
        int necessaryFloor;
        int currentFloor = 1;
        for (int i = 1; i <= ITERATIONS_COUNT; i++) {
            List<Cargo> waitingObjects = floorsPopulation.get(currentFloor);
            List<Cargo> objectsOnBoard = elevator.getObjectsOnBoard();
            if (waitingObjects.isEmpty() && objectsOnBoard.isEmpty()) {
                refreshObjectsOnFloor(currentFloor++, i);
                continue;
            }

            refreshObjectsOnFloor(currentFloor, i);

            necessaryFloor = getNecessaryFloor(currentFloor);
            if (necessaryFloor > currentFloor) {
                currentFloor++;
            } else if (necessaryFloor < currentFloor) {
                currentFloor--;
            }
        }
    }

    private void refreshObjectsOnFloor(int currentFloor, int stepNumber) {
        Map<Integer, List<Cargo>> floorsList = floorsPopulation;

        List<Cargo> unloadList = unloadAndAddToFloor(currentFloor);
        List<Cargo> remainingObjects = uploadToElevator(currentFloor);

        remainingObjects.addAll(unloadList);

        floorsList.remove(currentFloor);
        floorsList.put(currentFloor, remainingObjects);
        floorsPopulation = floorsList;

        saveStep(stepNumber, currentFloor, remainingObjects);
    }

    private void saveStep(int stepNumber, int currentFloor, List<Cargo> remainingObjects) {
        Map<Integer, List<Cargo>> floorsList = new HashMap<>(floorsPopulation);
        for (Map.Entry<Integer, List<Cargo>> stepFloorList : floorsList.entrySet()) {
            if (stepFloorList.getKey() == currentFloor) {
                int necessaryElevatorFloor = getNecessaryFloor(currentFloor);
                List<Cargo> onBoard = new ArrayList<>(elevator.getObjectsOnBoard());
                List<Cargo> remaining = new ArrayList<>(remainingObjects);
                Step step = new Step(stepNumber, currentFloor, setElevatorDirection(currentFloor, necessaryElevatorFloor), onBoard, remaining);
                this.stepList.add(step);
            } else {
                List<Cargo> remaining = new ArrayList<>(stepFloorList.getValue());
                Step step = new Step(stepNumber, stepFloorList.getKey(), remaining);
                this.stepList.add(step);
            }
        }
    }

    private List<Cargo> uploadToElevator(int currentFloor) {
        List<Cargo> waitingObjects = floorsPopulation.get(currentFloor);

        List<Cargo> objectsOnBoard = elevator.getObjectsOnBoard();
        int necessaryFloor;

        if (objectsOnBoard.size() == Elevator.MAX_ELEVATOR_CAPACITY) {
            return waitingObjects;
        } else if (objectsOnBoard.isEmpty()) {
            int freePlace = Elevator.MAX_ELEVATOR_CAPACITY;
            if (!waitingObjects.isEmpty()) {
                necessaryFloor = checkDirectionByWaitingObjects(currentFloor);
            } else if (currentFloor == Elevator.START_ELEVATOR_FLOOR) {
                necessaryFloor = 2;
            } else {
                necessaryFloor = 1;
            }
            Iterator<Cargo> waitingObjectsIter = waitingObjects.iterator();
            uploadElevator(currentFloor, objectsOnBoard, freePlace, necessaryFloor, waitingObjectsIter);
        } else {
            int freePlace = PassengerElevator.MAX_ELEVATOR_CAPACITY - elevator.getObjectsOnBoard().size();
            necessaryFloor = getNecessaryFloor(currentFloor);

            Iterator<Cargo> waitingObjectsIter = waitingObjects.iterator();
            uploadElevator(currentFloor, objectsOnBoard, freePlace, necessaryFloor, waitingObjectsIter);
        }
        elevator.setObjectsOnBoard(objectsOnBoard);

        return waitingObjects;
    }

    private int checkDirectionByWaitingObjects(int currentFloor) {
        List<Cargo> waitingObjects = floorsPopulation.get(currentFloor);

        long isUp = waitingObjects.stream().filter(c -> c.getTargetFloor() > currentFloor).count();
        long isDown = waitingObjects.stream().filter(c -> c.getTargetFloor() < currentFloor).count();
        if (isUp > isDown) {
            return (int) isUp;
        } else {
            return (int) isDown;
        }
    }

    private void uploadElevator(int currentFloor, List<Cargo> objectsOnBoard, int freePlace, int necessaryFloor, Iterator<Cargo> waitingObjectsIter) {
        while (waitingObjectsIter.hasNext() && freePlace > 0) {
            if (necessaryFloor > currentFloor) {
                Cargo person = waitingObjectsIter.next();
                if (person.getTargetFloor() > currentFloor) {
                    objectsOnBoard.add(person);
                    waitingObjectsIter.remove();
                    freePlace--;
                }
            } else if (necessaryFloor < currentFloor) {
                Cargo person = waitingObjectsIter.next();
                if (person.getTargetFloor() < currentFloor) {
                    objectsOnBoard.add(person);
                    waitingObjectsIter.remove();
                    freePlace--;
                }
            }
        }
    }

    private String setElevatorDirection(int currentFloor, int necessaryElevatorFloor) {
        if (currentFloor <= necessaryElevatorFloor) {
            return ELEVATOR_DIRECTION_UP;
        } else {
            return ELEVATOR_DIRECTION_DOWN;
        }
    }

    private List<Cargo> unloadAndAddToFloor(int floorNumber) {
        List<Cargo> bufferList = new ArrayList<>();
        if (checkElevatorLoad()) {
            List<Cargo> onBoard = elevator.getObjectsOnBoard();
            Iterator<Cargo> iterator = onBoard.iterator();

            while (iterator.hasNext()) {
                Cargo person = iterator.next();
                if (person.getTargetFloor() == floorNumber) {
                    person = personGenerator(floorNumber);
                    bufferList.add(person);
                    iterator.remove();
                }
            }
        }

        return bufferList;
    }

    private Cargo personGenerator(int startFloor) {
        Cargo person = new Person(startFloor, new Random().nextInt(buildingFloorsCount) + 1);
        while (startFloor == person.getTargetFloor()) {
            person.setTargetFloor(new Random().nextInt(buildingFloorsCount) + 1);
        }
        return person;
    }

    private boolean checkElevatorLoad() {
        return !elevator.getObjectsOnBoard().isEmpty();
    }

    private int getNecessaryFloor(int currentFloor) {
        int minNecessaryFloor = buildingFloorsCount;
        int maxNecessaryFloor = PassengerElevator.START_ELEVATOR_FLOOR;
        int necessaryFloor = 1;
        for (Cargo person : elevator.getObjectsOnBoard()) {
            if (minNecessaryFloor > person.getTargetFloor()) {
                minNecessaryFloor = person.getTargetFloor();
            }
            if (maxNecessaryFloor < person.getTargetFloor()) {
                maxNecessaryFloor = person.getTargetFloor();
            }
        }
        if (minNecessaryFloor > currentFloor && maxNecessaryFloor > currentFloor) {
            necessaryFloor = maxNecessaryFloor;
        } else if (minNecessaryFloor < currentFloor && maxNecessaryFloor < currentFloor) {
            necessaryFloor = minNecessaryFloor;
        }
        return necessaryFloor;
    }

    public List<Step> getSteps() {
        return stepList;
    }

    public int getBuildingFloorsCount() {
        return buildingFloorsCount;
    }
}
