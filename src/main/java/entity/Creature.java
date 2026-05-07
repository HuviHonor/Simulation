package entity;

import map.SimulationMap;
import navigation.Coordinates;

public abstract class Creature extends Entity {
    private final int speed;
    private int HP;

     public abstract void makeMove(Coordinates coordinates, SimulationMap map);

    public Creature(int speed, int HP) {
        if (speed > 0 && HP > 0) {
            this.speed = speed;
            this.HP = HP;
        } else {
            throw new IllegalArgumentException("Creature speed and HP can't be zero or less");
        }
    }

    public void takeDamage(int damage) {
        HP = HP - damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHP() {
        return HP;
    }

}
