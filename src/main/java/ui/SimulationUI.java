package ui;

import simulation.Simulation;
import java.util.Optional;
import java.util.Scanner;

public class SimulationUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int START = 1;
    private static final int PAUSE = 2;
    private static final int QUIT = 3;
    private final static String INCORRECT_INPUT_ERROR_MESSAGE =
            "Ошибка: нужно ввести число " +  START +  ", " + PAUSE + " или " + QUIT + "\n";

    public void print(Simulation simulation) {
            while (true) {
                String command = scanner.nextLine();
                int intCommand = parseCommand(command).orElse(0);
                switch (intCommand) {
                    case START -> simulation.resumeSimulation();
                    case PAUSE -> simulation.pauseSimulation();
                    case QUIT -> {
                        simulation.pauseSimulation();
                        return;
                    }
                    default -> System.out.println(INCORRECT_INPUT_ERROR_MESSAGE);
                }
            }
        }


    private  Optional<Integer> parseCommand(String command) {
        try {
            return Optional.of(Integer.parseInt(command));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
