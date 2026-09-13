package render;

import entity.*;
import simulationmap.SimulationMap;
import navigation.Coordinates;

public class ConsoleRenderer {
    private static final String GRASS_EMOJI = "🌿";
    private static final String ROCK_EMOJI = "🪨";
    private static final String TREE_EMOJI = "🌳";
    private static final String HERBIVORE_EMOJI = "🐑";
    private static final String PREDATOR_EMOJI = "🐅";
    private static final String EMPTY_CELL = "..";

    public void render(SimulationMap simulationMap, int turnCounter) {
        System.out.print("\033[3J\033[H");
        System.out.flush();
        for (int y = 0; y < simulationMap.getHeight(); y++) {
            for(int x = 0; x < simulationMap.getWidth(); x++) {
               Entity entity =  simulationMap.getEntity(new Coordinates(x, y));
                switch (entity) {
                    case Grass ignored -> System.out.print(GRASS_EMOJI);
                    case Rock ignored -> System.out.print(ROCK_EMOJI);
                    case Tree ignored -> System.out.print(TREE_EMOJI);
                    case Herbivore ignored -> System.out.print(HERBIVORE_EMOJI);
                    case Predator ignored -> System.out.print(PREDATOR_EMOJI);
                    case null, default -> System.out.print(EMPTY_CELL);
                }
            }
            System.out.println();
        }
        System.out.println("Счетчик ходов: " + turnCounter);
        System.out.println("1. Запустить симуляцию");
        System.out.println("2. Остановить симуляцию");
        System.out.println("3. Выйти");
        System.out.print(">> ");
    }
}
