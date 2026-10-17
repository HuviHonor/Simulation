package entity;

import navigation.Coordinates;
import navigation.PathFinder;
import simulationmap.SimulationMap;

import java.util.List;

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
        List<Coordinates> path = PathFinder.find(currentCoordinates, simulationMap, this::isFood);
        for (int i = 0; i < speed && i < path.size(); i++) {
            Coordinates nextCoordinates = path.get(i);
            boolean isTarget = (i == path.size() - 1);
            if (isTarget) {
                interactWithFood(currentCoordinates, nextCoordinates, simulationMap);
                return;
            }
            makeStep(currentCoordinates, nextCoordinates, simulationMap);
            currentCoordinates = nextCoordinates;
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
