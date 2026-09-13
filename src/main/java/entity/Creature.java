package entity;

import simulationmap.SimulationMap;
import navigation.Coordinates;

public abstract class Creature extends Entity {
    private final int speed;
    private int hp;

    public abstract void makeMove(Coordinates coordinates, SimulationMap simulationMap);

    public Creature(int speed, int hp) {
        if (speed > 0 && hp > 0) {
            this.speed = speed;
            this.hp = hp;
        } else {
            throw new IllegalArgumentException("Creature speed and hp can't be zero or less");
        }
    }

    public void takeDamage(int damage) {
        hp = hp - damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public void makeStep (Coordinates from, Coordinates to, SimulationMap simulationMap) {
        simulationMap.removeEntity(from);
        simulationMap.setEntity(to, this);
    }

}
