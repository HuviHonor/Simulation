package entity;

import map.SimulationMap;
import navigation.Coordinates;
import navigation.PathFinder;

public class Predator extends Creature {
    private final int attackPower;

    public Predator(int predatorSpeed, int predatorHP, int attackPower) {
        super(predatorSpeed, predatorHP);
        if (attackPower <= 0) {
            throw new IllegalArgumentException("Predator attack power can't be zero or less ");
        }
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(Coordinates currentCoordinates, SimulationMap map) {
        for(int i = 0; i < this.getSpeed(); i++) {
            Coordinates nextStep = PathFinder.findPath(currentCoordinates, map, entity -> entity instanceof Herbivore);
            if (nextStep == null) {
                return;
            }
            Entity checkingEntity = map.getEntity(nextStep);
            if (checkingEntity instanceof Herbivore herbivore) {
                attack(herbivore, map);
            } else {
                this.makeStep(currentCoordinates, nextStep, map);
                currentCoordinates = nextStep;
            }
        }
    }

    private void attack(Creature herbivore, SimulationMap map) {
        herbivore.takeDamage(attackPower);
        if (herbivore.getHP() <= 0) {
            Coordinates herbivoreCoordinates = map.entityCoordinates(herbivore);
            map.removeEntity(herbivoreCoordinates);
        }
    }
    public int getAttackPower() {
        return attackPower;
    }
}
