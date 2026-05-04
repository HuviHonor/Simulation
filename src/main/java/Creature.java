public abstract class Creature extends Entity {
    private final int creatureSpeed;
    private int creatureHP;

     public abstract void makeMove(Coordinates coordinates, SimulationMap map);

    public Creature(int creatureSpeed, int creatureHP) {
        if (creatureSpeed > 0 && creatureHP > 0) {
            this.creatureSpeed = creatureSpeed;
            this.creatureHP = creatureHP;
        } else {
            throw new IllegalArgumentException("Creature speed and HP can't be zero or less");
        }
    }

    public void takeDamage(int damage) {
        creatureHP = creatureHP - damage;
    }

    public int getCreatureSpeed() {
        return creatureSpeed;
    }

    public int getCreatureHP() {
        return creatureHP;
    }

}
