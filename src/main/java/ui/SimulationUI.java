package ui;

import simulation.Simulation;
import java.util.Optional;
import java.util.Scanner;

public class SimulationUI {
    private static final Scanner scanner = new Scanner(System.in);
    public static final int START = 1;
    public static final int PAUSE = 2;
    public static final int QUIT = 3;
    private final static String INCORRECT_INPUT_ERROR_MESSAGE =
            "Ошибка: нужно ввести число %d, %d или %d".formatted(START, PAUSE, QUIT);

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
