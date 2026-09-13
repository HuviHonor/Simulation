package entity;

import simulationmap.SimulationMap;
import navigation.Coordinates;
import navigation.PathFinder;

public class Herbivore extends Creature {
    public Herbivore(int speed, int hp) {
        super (speed, hp);
    }

    @Override
    public void makeMove(Coordinates currentCoordinates, SimulationMap simulationMap) {
        for (int i = 0; i < this.getSpeed(); i++ ) {
            Coordinates nextStep = PathFinder.find(currentCoordinates, simulationMap , entity -> entity instanceof Grass);
            if (nextStep == null) {
                return;
            }
            Entity checkingEntity = simulationMap.getEntity(nextStep);
            if (checkingEntity instanceof Grass) {
                eat(simulationMap, nextStep);
                this.makeStep(currentCoordinates, nextStep, simulationMap);
                return;
            } else {
                this.makeStep(currentCoordinates, nextStep, simulationMap);
                currentCoordinates = nextStep;
            }
        }
    }

    private void eat(SimulationMap simulationMap, Coordinates nextStep) {
        simulationMap.removeEntity(nextStep);
    }
}
