import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimulationMap {
    private final int height;
    private final int width;

    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public SimulationMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isWithinBounds(Coordinates coordinates) {
        return (Objects.nonNull(coordinates) && ((coordinates.getX() >= 0 && coordinates.getX() < width )  && (coordinates.getY() >= 0) && coordinates.getY() < height));
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        Objects.requireNonNull(entity, "Entity can't be null");
        validateCoordinates(coordinates);
        if (entities.containsKey(coordinates)) {
            throw new IllegalArgumentException("There is already an entity at these coordinates. Delete it or select other coordinates.");
        }
        entities.put(coordinates, entity);
        System.out.println("Entity: " + entity + " was added on map with coordinates: " + coordinates);
    }

    public Entity getEntity (Coordinates coordinates) {
        validateCoordinates(coordinates);
        return entities.get(coordinates);
    }

    public void removeEntity(Coordinates coordinates) {
        validateCoordinates(coordinates);
        entities.remove(coordinates);
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        if (x < 0  || y < 0) {
            throw new IllegalArgumentException("Coordinates can't be negative");
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
