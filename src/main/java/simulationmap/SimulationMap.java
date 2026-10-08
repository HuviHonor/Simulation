package simulationmap;

import entity.Entity;
import navigation.Coordinates;

import java.util.*;

public class SimulationMap {
    private final int height;
    private final int width;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public SimulationMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isWithinBounds(Coordinates coordinates) {
        return coordinates.x() >= 0 && coordinates.x() < width
                && coordinates.y() >= 0 && coordinates.y() < height;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        Objects.requireNonNull(entity, "Entity can't be null");
        validateCoordinates(coordinates);
        if (entities.containsKey(coordinates)) {
            throw new IllegalArgumentException("There is already an entity at these coordinates. Delete it or select other coordinates.");
        }
        entities.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        validateCoordinates(coordinates);
        return entities.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        validateCoordinates(coordinates);
        entities.remove(coordinates);
    }

    public Coordinates getEntityCoordinates(Entity entity) {
        return entities.entrySet().stream()
                .filter(e -> e.getValue() == entity)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Entity coordinates not found"));
    }

    public List<Entity> getEntities() {
        return new ArrayList<>(entities.values());
    }

    public boolean isEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public boolean containsEntity(Entity entity) {
        return entities.containsValue(entity);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private void validateCoordinates(Coordinates coordinates) {
        Objects.requireNonNull(coordinates, "Coordinates cannot be null");
        if (!this.isWithinBounds(coordinates)) {
            throw new IllegalArgumentException("Coordinates %s are out of map bounds.".formatted(coordinates));
        }
    }
}