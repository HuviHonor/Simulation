package action;

import entity.Entity;
import navigation.Coordinates;
import simulationmap.SimulationMap;

import java.util.Objects;
import java.util.function.Supplier;

import static simulationmap.SimulationMapUtils.countEmptyCells;
import static simulationmap.SimulationMapUtils.findRandomEmptyCell;

public class InitEntitiesAction implements Action {
    private final Supplier<Entity> supplier;
    private final int entitiesCount;

    public InitEntitiesAction(Supplier<Entity> supplier, int entitiesCount) {
        if (entitiesCount <= 0) {
            throw new IllegalArgumentException("Count of entities can't be zero or less");
        }
        Objects.requireNonNull(supplier, "Supplier can't be null");
        this.supplier = supplier;
        this.entitiesCount = entitiesCount;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        if (entitiesCount > countEmptyCells(simulationMap)) {
            throw new IllegalArgumentException("The number of entities being added is greater than the number of empty cells");
        }
        for (int i = 0; i < entitiesCount; i++) {
            Coordinates randomEmptyCellCoordinates = findRandomEmptyCell(simulationMap);
            simulationMap.setEntity(randomEmptyCellCoordinates, supplier.get());
        }
    }
}
