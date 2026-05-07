package navigation;

import entity.Entity;
import map.SimulationMap;

import java.util.*;
import java.util.function.Predicate;

public class PathFinder {

    public static Coordinates findPath(Coordinates currentCreatureCoordinate, SimulationMap map, Predicate<Entity> targetCondition) {
        Deque<Coordinates> queue = new ArrayDeque<>();
        queue.offer(currentCreatureCoordinate);
        Map<Coordinates, Coordinates> pathRecreatingMap = new HashMap<>();
        pathRecreatingMap.put(currentCreatureCoordinate, null);

        while (!queue.isEmpty()) {
            Coordinates currentCoordinate = queue.poll();
            Entity entity = map.getEntity(currentCoordinate);
            if (entity != null && targetCondition.test(entity)) {
                Coordinates nextStep = currentCoordinate;
                while (pathRecreatingMap.get(currentCoordinate) != null) {
                    nextStep = currentCoordinate;
                    currentCoordinate = pathRecreatingMap.get(currentCoordinate);
                }
                return nextStep;
            } else {
                int currentX = currentCoordinate.getX();
                int currentY = currentCoordinate.getY();
                Coordinates oneStepRightNeighbor = new Coordinates(currentX + 1, currentY);
                Coordinates oneStepUpNeighbor = new Coordinates(currentX, currentY + 1);
                Coordinates oneStepLeftNeighbor = new Coordinates(currentX - 1, currentY);
                Coordinates oneStepDownNeighbor = new Coordinates(currentX, currentY - 1);

                addNeighborIfValid(oneStepRightNeighbor, currentCoordinate, queue, pathRecreatingMap, map, targetCondition);
                addNeighborIfValid(oneStepUpNeighbor, currentCoordinate, queue, pathRecreatingMap, map, targetCondition);
                addNeighborIfValid(oneStepLeftNeighbor, currentCoordinate, queue, pathRecreatingMap, map, targetCondition);
                addNeighborIfValid(oneStepDownNeighbor, currentCoordinate, queue, pathRecreatingMap, map, targetCondition);
            }
        }
        return null;
    }

    private static void addNeighborIfValid(Coordinates neighbor, Coordinates currentCoordinate, Deque<Coordinates> queue,
                                           Map<Coordinates, Coordinates> pathRecreatingMap, SimulationMap map, Predicate<Entity> targetCondition) {
        if (!pathRecreatingMap.containsKey(neighbor) && isPassable(neighbor, map, targetCondition)) {
            queue.offer(neighbor);
            pathRecreatingMap.put(neighbor, currentCoordinate);
        }
    }

    private static boolean isPassable(Coordinates neighbor, SimulationMap map, Predicate<Entity> targetCondition) {
        return (map.isWithinBounds(neighbor)) && (map.isEmpty(neighbor) || targetCondition.test(map.getEntity(neighbor)));
    }
}
