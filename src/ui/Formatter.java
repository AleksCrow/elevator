package ui;

import elevate.ElevateProccess;
import model.Cargo;
import model.Step;

import java.util.Comparator;
import java.util.List;

public class Formatter implements UI {

    @Override
    public void display(ElevateProccess elevateProccess) {
        elevateProccess.transport();
        List<Step> stepList = elevateProccess.getSteps();
        int maxStepNumber = stepList.stream().max(Comparator.comparing(Step::getStepNumber)).get().getStepNumber();

        StringBuilder output = new StringBuilder();
        for (int i = 1; i <= maxStepNumber; i++) {
            output.append(String.format("%18s %s ===%n", "=== Step", i));
            for (int j = elevateProccess.getBuildingFloorsCount(); j >= 1; j--) {

                StringBuilder remainingPersons = new StringBuilder();
                StringBuilder personsOnBoard = new StringBuilder(String.format("%15s", ""));
                String elevatorDirection = " ";

                for (Step step : stepList) {
                    if (step.getStepNumber() == i && step.getStepFloor() == j) {
                        if (step.getElevatorDirection() != null && !step.getElevatorDirection().equals("")) {
                            elevatorDirection = step.getElevatorDirection();
                        }
                        if (step.getPersonsOnBoard() != null && !step.getPersonsOnBoard().isEmpty()) {
                            personsOnBoard = new StringBuilder();
                            for (Cargo person : step.getPersonsOnBoard()) {
                                personsOnBoard.append(" ").append(person.getTargetFloor());
                            }
                        }
                        if (step.getRemainingPersons() != null && !step.getRemainingPersons().isEmpty()) {
                            for (Cargo person : step.getRemainingPersons()) {
                                remainingPersons.append(" ").append(person.getTargetFloor());
                            }
                        }
                    }
                }
                output.append(String.format(" %4s | %s %15s %s | %s%n", j, elevatorDirection, personsOnBoard, elevatorDirection, remainingPersons));
            }
        }
        System.out.println(output);
    }
}
