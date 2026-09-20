package simulationmap;

import entity.Creature;
import entity.Entity;
import navigation.Coordinates;
import java.util.*;
import java.util.stream.IntStream;

public class SimulationMap {
    private final int height;
    private final int width;
    private final Map<Coordinates, Entity> entities = new HashMap<>();
    private final Random random = new Random();

    public SimulationMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isWithinBounds(Coordinates coordinates) {
        return ((coordinates.x() >= 0 && coordinates.x() < width )
                && (coordinates.y() >= 0) && coordinates.y() < height);
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        Objects.requireNonNull(entity, "Entity can't be null");
        validateCoordinates(coordinates);
        if (entities.containsKey(coordinates)) {
            throw new IllegalArgumentException("There is already an entity at these coordinates. Delete it or select other coordinates.");
        }
        entities.put(coordinates, entity);
    }

    public Entity getEntity (Coordinates coordinates) {
        validateCoordinates(coordinates);
        return entities.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        validateCoordinates(coordinates);
        entities.remove(coordinates);
    }

    public Coordinates entityCoordinates(Entity entity) {
        return entities.entrySet().stream()
                .filter(e -> e.getValue() == entity)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException("Entity coordinates not found"));
    }

    private void validateCoordinates (Coordinates coordinates) {
        Objects.requireNonNull(coordinates, "Coordinates cannot be null");
        if (!this.isWithinBounds(coordinates)) {
            throw new IllegalArgumentException("Coordinates " + coordinates + " are  out of map bounds.");
        }
    }

    public boolean isEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public int getAvailableCellsCount() {
        return height * width - entities.size();
    }

    public List<Coordinates> getAllCreaturesCoordinates() {
        return entities.entrySet().stream()
                .filter(e -> e.getValue() instanceof Creature)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Coordinates getRandomEmptyCell() {

        if (getAvailableCellsCount() == 0) {
            throw new IllegalArgumentException("There are no available empty cells ");
        }
        List<Coordinates> empyCellsList = IntStream.range(0, width)
                .boxed()
                .flatMap(x -> IntStream.range(0, height).mapToObj(y -> new Coordinates(x, y)))
                .filter(e -> !entities.containsKey(e))
                .toList();
        int randomIndex = random.nextInt(empyCellsList.size());
        return empyCellsList.get(randomIndex);
    }

    public int countEntitiesOfType(Class<?> type) {
        return (int) entities.entrySet().stream()
                .filter(e -> type.isInstance(e.getValue()))
                .count();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

