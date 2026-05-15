package render;

import entity.*;
import map.SimulationMap;
import navigation.Coordinates;

public class ConsoleRenderer {
    private static final String GRASS_EMOJI = "🌿";
    private static final String ROCK_EMOJI = "🪨";
    private static final String TREE_EMOJI = "🌳";
    private static final String HERBIVORE_EMOJI = "🐑";
    private static final String PREDATOR_EMOJI = "🐺";
    private static final String EMPTY_CELL = "..";

    public void render(SimulationMap map) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
               Entity entity =  map.getEntity(new Coordinates(x, y));
                switch (entity) {
                    case Grass grass -> System.out.print(GRASS_EMOJI);
                    case Rock rock -> System.out.print(ROCK_EMOJI);
                    case Tree tree -> System.out.print(TREE_EMOJI);
                    case Herbivore herbivore -> System.out.print(HERBIVORE_EMOJI);
                    case Predator predator -> System.out.print(PREDATOR_EMOJI);
                    case null, default -> System.out.print(EMPTY_CELL);
                }
            }
            System.out.println();
        }
    }
}
