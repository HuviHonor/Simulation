package action;

import entity.Entity;
import simulationmap.SimulationMap;
import navigation.Coordinates;

import java.util.Objects;
import java.util.function.Supplier;

public class InitEntitiesAction implements Action {
    private final Supplier<Entity> supplier;
    private final int numberOfEntities;

    public InitEntitiesAction(Supplier<Entity> supplier, int numberOfEntities) {
        if (numberOfEntities <= 0 ) {
            throw new IllegalArgumentException("Numbers of entities can't be zero or less");
        }
        Objects.requireNonNull(supplier, "Supplier can't be null");
        this.supplier = supplier;
        this.numberOfEntities = numberOfEntities;
    }

    @Override
    public void execute(SimulationMap simulationMap) {
        if (numberOfEntities > simulationMap.getAvailableCellsCount()) {
            throw new IllegalArgumentException("The number of creatures being added is greater than the number of available cells");
        }
        for (int i = 0; i < numberOfEntities; i++ ) {
            Coordinates randomEmptyCellCoordinates = simulationMap.getRandomEmptyCell();
            simulationMap.setEntity(randomEmptyCellCoordinates, supplier.get());
        }
    }
}
