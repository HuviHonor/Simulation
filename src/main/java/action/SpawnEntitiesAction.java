package action;

import entity.Entity;
import map.SimulationMap;
import navigation.Coordinates;
import java.util.Objects;
import java.util.function.Supplier;

public class SpawnEntitiesAction<T extends Entity> implements Action  {
    private final int minEntityCount;
    private final Supplier<T> supplier;
    private final Class<T> type;

    public SpawnEntitiesAction(Class<T> type ,int minEntityCount, Supplier<T> supplier) {
        Objects.requireNonNull(type, "Type can't be null");
        Objects.requireNonNull(supplier, "Supplier can't be null");
        if (minEntityCount <= 0 ) {
            throw new IllegalArgumentException("The minimum amount of entity cannot be less than or equal to zero ");
        }
        this.minEntityCount = minEntityCount;
        this.supplier = supplier;
        this.type = type;
    }

    @Override
    public void execute(SimulationMap map) {
        int currentEntityCount = map.countEntitiesOfType(type);
        if (currentEntityCount < minEntityCount) {
            int numberOfEntity = minEntityCount - currentEntityCount;
            if (numberOfEntity > map.getAvailableCellsCount()) {
                throw new IllegalArgumentException("The number of entity being added is greater than the number of available cells");
            }
            for (int i = 0; i < numberOfEntity; i++) {
                Coordinates randomEmptyCellCoordinates = map.getRandomEmptyCell();
                map.setEntity(randomEmptyCellCoordinates, supplier.get());
            }
        }
    }
}
