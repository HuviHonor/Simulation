package entity;

import navigation.Coordinates;
import simulationmap.SimulationMap;

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
    protected boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    protected void interactWithFood(Coordinates currentCoordinates, Coordinates targetCoordinates, SimulationMap simulationMap) {
        Entity targetEntity = simulationMap.getEntity(targetCoordinates);
        if (!(targetEntity instanceof Creature targetCreature)) {
            throw new IllegalStateException("Expected target for predator is creature. %s has %s."
                    .formatted(targetCoordinates, targetEntity));
        }
        targetCreature.takeDamage(attackPower);
        if (targetCreature.getHp() <= 0) {
            simulationMap.removeEntity(targetCoordinates);
        }
    }
}
