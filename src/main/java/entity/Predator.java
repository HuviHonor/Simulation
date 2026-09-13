package entity;

import simulationmap.SimulationMap;
import navigation.Coordinates;
import navigation.PathFinder;

public class Predator extends Creature {
    private final int attackPower;

    public Predator(int speed, int hp, int attackPower) {
        super(speed, hp);
        if (attackPower <= 0) {
            throw new IllegalArgumentException("Predator attack power can't be zero or less ");
        }
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(Coordinates currentCoordinates, SimulationMap simulationMap) {
        for(int i = 0; i < this.getSpeed(); i++) {
            Coordinates nextStep = PathFinder.find(currentCoordinates, simulationMap, entity -> entity instanceof Herbivore);
            if (nextStep == null) {
                return;
            }
            Entity checkingEntity = simulationMap.getEntity(nextStep);
            if (checkingEntity instanceof Herbivore herbivore) {
                attack(herbivore, simulationMap);
            } else {
                this.makeStep(currentCoordinates, nextStep, simulationMap);
                currentCoordinates = nextStep;
            }
        }
    }

    private void attack(Creature herbivore, SimulationMap simulationMap) {
        herbivore.takeDamage(attackPower);
        if (herbivore.getHp() <= 0) {
            Coordinates herbivoreCoordinates = simulationMap.entityCoordinates(herbivore);
            simulationMap.removeEntity(herbivoreCoordinates);
        }
    }
    public int getAttackPower() {
        return attackPower;
    }
}
