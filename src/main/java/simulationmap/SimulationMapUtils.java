package simulationmap;

import entity.Entity;
import navigation.Coordinates;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public final class SimulationMapUtils {
    private static final Random RANDOM = new Random();

    private SimulationMapUtils() {}

    public static Coordinates findRandomEmptyCell(SimulationMap simulationMap) {
        if (countEmptyCells(simulationMap) == 0) {
            throw new IllegalStateException("There are no empty cells");
        }
        List<Coordinates> emptyCells = IntStream.range(0, simulationMap.getWidth())
                .boxed()
                .flatMap(x -> IntStream.range(0, simulationMap.getHeight()).mapToObj(y -> new Coordinates(x, y)))
                .filter(simulationMap::isEmpty)
                .toList();
        int randomIndex = RANDOM.nextInt(emptyCells.size());
        return emptyCells.get(randomIndex);
    }

    public static int countEmptyCells(SimulationMap simulationMap) {
        List<Entity> entities = simulationMap.getEntities();
        return simulationMap.getHeight() * simulationMap.getWidth() - entities.size();
    }
}
