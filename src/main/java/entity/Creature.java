package entity;

import navigation.Coordinates;
import navigation.PathFinder;
import simulationmap.SimulationMap;

public abstract class Creature extends Entity {
    private final int speed;
    private int hp;

    protected Creature(int speed, int hp) {
        if (speed <= 0 || hp <= 0) {
            throw new IllegalArgumentException("Creature speed and hp can't be zero or less");
        }
        this.speed = speed;
        this.hp = hp;
    }

    public final void makeMove(SimulationMap simulationMap) {
        Coordinates currentCoordinates = simulationMap.getEntityCoordinates(this);
        for (int i = 0; i < speed; i++) {
            Coordinates nextStep = PathFinder.find(currentCoordinates, simulationMap, this::isFood);
            if (nextStep == null) {
                return;
            }
            Entity nextStepEntity = simulationMap.getEntity(nextStep);
            if (isFood(nextStepEntity)) {
                interactWithFood(currentCoordinates, nextStep, simulationMap);
                return;
            }
            makeStep(currentCoordinates, nextStep, simulationMap);
            currentCoordinates = nextStep;
        }
    }

    public void takeDamage(int damage) {
        hp = hp - damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public int getHp() {
        return hp;
    }

    protected void makeStep(Coordinates from, Coordinates to, SimulationMap simulationMap) {
        simulationMap.removeEntity(from);
        simulationMap.setEntity(to, this);
    }

    protected abstract void interactWithFood(Coordinates currentCoordinates,
                                             Coordinates targetCoordinates, SimulationMap simulationMap);

    protected abstract boolean isFood(Entity entity);

}
