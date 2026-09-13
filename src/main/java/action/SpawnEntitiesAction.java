package action;

import entity.Entity;
import simulationmap.SimulationMap;
import navigation.Coordinates;
import java.util.Objects;
import java.util.function.Supplier;

public class SpawnEntitiesAction<T extends Entity> implements Action  {
    private final int minEntityCount;
    private final int spawnUpToCount;
    private final Supplier<T> supplier;
    private final Class<T> type;

    public SpawnEntitiesAction(Class<T> type ,int minEntityCount,int spawnUpToCount, Supplier<T> supplier) {
        Objects.requireNonNull(type, "Type can't be null");
        Objects.requireNonNull(supplier, "Supplier can't be null");
        if (minEntityCount <= 0 ) {
            throw new IllegalArgumentException("The minimum amount of entity cannot be less than or equal to zero ");
        }
        this.minEntityCount = minEntityCount;
        this.spawnUpToCount = spawnUpToCount;
        this.supplier = supplier;
        this.type = type;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        int currentEntityCount = simulationMap.countEntitiesOfType(type);
        if (currentEntityCount < minEntityCount) {
            int numberOfEntity = spawnUpToCount - currentEntityCount;
            numberOfEntity = Math.min(numberOfEntity, simulationMap.getAvailableCellsCount());
            for (int i = 0; i < numberOfEntity; i++) {
                Coordinates randomEmptyCellCoordinates = simulationMap.getRandomEmptyCell();
                simulationMap.setEntity(randomEmptyCellCoordinates, supplier.get());
            }
        }
    }
}
