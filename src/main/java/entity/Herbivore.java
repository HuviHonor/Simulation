package entity;

import map.SimulationMap;
import navigation.Coordinates;
import navigation.PathFinder;

public class Herbivore extends Creature {
    public Herbivore(int herbivoreSpeed, int herbivoreHP) {
        super (herbivoreSpeed, herbivoreHP);
    }

    @Override
    public void makeMove(Coordinates currentCoordinates, SimulationMap map) {
        for (int i = 0; i < this.getSpeed(); i++ ) {
            Coordinates nextStep = PathFinder.findPath(currentCoordinates, map , entity -> entity instanceof Grass);
            if (nextStep == null) {
                return;
            }
            Entity checkingEntity = map.getEntity(nextStep);
            if (checkingEntity instanceof Grass) {
                eat(map, nextStep);
                this.makeStep(currentCoordinates, nextStep, map);
                return;
            } else {
                this.makeStep(currentCoordinates, nextStep, map);
                currentCoordinates = nextStep;
            }
        }
    }

    private void eat(SimulationMap map, Coordinates nextStep) {
        map.removeEntity(nextStep);
    }
}
