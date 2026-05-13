package map;

import entity.Creature;
import entity.Entity;
import navigation.Coordinates;

import java.util.*;

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
        return ((coordinates.getX() >= 0 && coordinates.getX() < width )
                && (coordinates.getY() >= 0) && coordinates.getY() < height);
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
        int entityRandomX = random.nextInt(width);
        int entityRandomY = random.nextInt(height);
        Coordinates entityCoordinates = new Coordinates(entityRandomX, entityRandomY);
        while (!isEmpty(entityCoordinates)) {
            entityRandomX = random.nextInt(width);
            entityRandomY = random.nextInt(height);
            entityCoordinates = new Coordinates(entityRandomX, entityRandomY);
        }
        return entityCoordinates;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

