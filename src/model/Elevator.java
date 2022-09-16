package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Elevator {

    public static final int MAX_ELEVATOR_CAPACITY = 5;
    public static final int START_ELEVATOR_FLOOR = 1;

    private List<Cargo> objectsOnBoard;

    public Elevator() {
        this.objectsOnBoard = new ArrayList<>();
    }

    public List<Cargo> getObjectsOnBoard() {
        return objectsOnBoard;
    }

    public void setObjectsOnBoard(List<Cargo> objectsOnBoard) {
        this.objectsOnBoard = objectsOnBoard;
    }
}
