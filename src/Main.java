import elevate.ElevateProccess;
import generation.RandomPopulator;
import model.PassengerElevator;
import ui.Formatter;
import ui.UI;

public class Main {
    public static void main(String[] args) {
        ElevateProccess elevateProccess = new ElevateProccess(new RandomPopulator(), new PassengerElevator());
        ElevateProccess.ITERATIONS_COUNT = 40;
        UI formatter = new Formatter();
        formatter.display(elevateProccess);
    }
}