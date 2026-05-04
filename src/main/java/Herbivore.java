public class Herbivore extends Creature {
    public Herbivore(int herbivoreHP, int herbivoreSpeed) {
        super(herbivoreSpeed, herbivoreHP);
    }

    @Override
    public void makeMove(Coordinates currentCoordinates, SimulationMap map) {
        Coordinates nextStep = PathFinding.pathFind(currentCoordinates, map ,entity -> entity instanceof Grass);
        if (nextStep == null) {
            return;
        }
        Entity checkingEntity = map.getEntity(nextStep);
        if (checkingEntity instanceof Grass grass) {
            eat(grass, map);
        } else {
            Entity herbivore = map.getEntity(currentCoordinates);
            map.removeEntity(currentCoordinates);
            map.setEntity(nextStep, herbivore);
        }
    }

    private void eat(Grass grass, SimulationMap map) {
        Coordinates grassCoordinates = map.entityCoordinates(grass);
        map.removeEntity(grassCoordinates);
    }
}
