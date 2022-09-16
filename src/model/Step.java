package model;

import java.util.List;

public class Step {

    private final int stepNumber;
    private final int stepFloor;
    private final String elevatorDirection;
    private final List<Cargo> personsOnBoard;
    private final List<Cargo> remainingPersons;

    public Step(int stepNumber, int stepFloor, String elevatorDirection, List<Cargo> personsOnBoard, List<Cargo> remainingPersons) {
        this.stepNumber = stepNumber;
        this.stepFloor = stepFloor;
        this.elevatorDirection = elevatorDirection;
        this.personsOnBoard = personsOnBoard;
        this.remainingPersons = remainingPersons;
    }

    public Step(int stepNumber, int stepFloor, List<Cargo> remainingPersons) {
        this.stepNumber = stepNumber;
        this.stepFloor = stepFloor;
        this.remainingPersons = remainingPersons;
        this.personsOnBoard = null;
        this.elevatorDirection = "";
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public int getStepFloor() {
        return stepFloor;
    }

    public String getElevatorDirection() {
        return elevatorDirection;
    }

    public List<Cargo> getPersonsOnBoard() {
        return personsOnBoard;
    }

    public List<Cargo> getRemainingPersons() {
        return remainingPersons;
    }
}
