package action;

import entity.Entity;
import navigation.Coordinates;
import simulationmap.SimulationMap;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static simulationmap.SimulationMapUtils.countEmptyCells;
import static simulationmap.SimulationMapUtils.findRandomEmptyCell;

public class SpawnEntitiesAction<T extends Entity> implements Action {
    private final int minEntityCount;
    private final int spawnUpToCount;
    private final Supplier<T> supplier;
    private final Class<T> type;

    public SpawnEntitiesAction(Class<T> type, int minEntityCount, int spawnUpToCount, Supplier<T> supplier) {
        Objects.requireNonNull(type, "Type can't be null");
        Objects.requireNonNull(supplier, "Supplier can't be null");
        if (minEntityCount <= 0) {
            throw new IllegalArgumentException("The minimum amount of entity cannot be less than or equal to zero");
        }
        this.minEntityCount = minEntityCount;
        this.spawnUpToCount = spawnUpToCount;
        this.supplier = supplier;
        this.type = type;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        int currentEntityCount = countEntitiesOfType(simulationMap);
        if (currentEntityCount < minEntityCount) {
            int entitiesToSpawnCount = spawnUpToCount - currentEntityCount;
            entitiesToSpawnCount = Math.min(entitiesToSpawnCount, countEmptyCells(simulationMap));
            for (int i = 0; i < entitiesToSpawnCount; i++) {
                Coordinates randomEmptyCellCoordinates = findRandomEmptyCell(simulationMap);
                simulationMap.setEntity(randomEmptyCellCoordinates, supplier.get());
            }
        }
    }

    private int countEntitiesOfType(SimulationMap simulationMap) {
        List<Entity> entities = simulationMap.getEntities();
        return (int) entities.stream()
                .filter(type::isInstance)
                .count();
    }
}
