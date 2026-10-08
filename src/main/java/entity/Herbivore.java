package entity;

import navigation.Coordinates;
import simulationmap.SimulationMap;

public class Herbivore extends Creature {
    public Herbivore(int speed, int hp) {
        super(speed, hp);
    }

    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void interactWithFood(Coordinates currentCoordinates, Coordinates targetCoordinates, SimulationMap simulationMap) {
        simulationMap.removeEntity(targetCoordinates);
        makeStep(currentCoordinates, targetCoordinates, simulationMap);
    }
}