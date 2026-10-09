package navigation;

import entity.Entity;
import simulationmap.SimulationMap;

import java.util.*;
import java.util.function.Predicate;

public final class PathFinder {
    private static final List<Coordinates> SHIFT_COORDINATES = List.of(
            new Coordinates(1, 0), new Coordinates(0, 1), new Coordinates(-1, 0), new Coordinates(0, -1));

    private PathFinder() {
    }

    public static Coordinates find(Coordinates start, SimulationMap simulationMap, Predicate<Entity> targetCondition) {
        Deque<Coordinates> queue = new ArrayDeque<>();
        queue.offer(start);
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            if (!simulationMap.isEmpty(current) && targetCondition.test(simulationMap.getEntity(current))) {
                Coordinates nextStep = current;
                while (cameFrom.get(current) != null) {
                    nextStep = current;
                    current = cameFrom.get(current);
                }
                return nextStep;
            }
            for (Coordinates shift : SHIFT_COORDINATES) {
                Coordinates neighbor = new Coordinates(current.x() + shift.x(), current.y() + shift.y());
                addNeighborIfValid(neighbor, current, queue, cameFrom, simulationMap, targetCondition);
            }
        }
        return null;
    }

    private static void addNeighborIfValid(Coordinates neighbor, Coordinates current, Deque<Coordinates> queue,
                                           Map<Coordinates, Coordinates> cameFrom, SimulationMap simulationMap, Predicate<Entity> targetCondition) {
        if (!cameFrom.containsKey(neighbor) && canVisit(neighbor, simulationMap, targetCondition)) {
            queue.offer(neighbor);
            cameFrom.put(neighbor, current);
        }
    }

    private static boolean canVisit(Coordinates neighbor, SimulationMap simulationMap, Predicate<Entity> targetCondition) {
        return (simulationMap.isWithinBounds(neighbor)) && (simulationMap.isEmpty(neighbor) || targetCondition.test(simulationMap.getEntity(neighbor)));
    }
}
