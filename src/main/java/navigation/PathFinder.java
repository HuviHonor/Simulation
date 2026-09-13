package navigation;

import entity.Entity;
import simulationmap.SimulationMap;

import java.util.*;
import java.util.function.Predicate;

public class PathFinder {

    public static Coordinates find(Coordinates currentCreatureCoordinate, SimulationMap simulationMap, Predicate<Entity> targetCondition) {
        Deque<Coordinates> queue = new ArrayDeque<>();
        queue.offer(currentCreatureCoordinate);
        Map<Coordinates, Coordinates> pathRecreatingMap = new HashMap<>();
        pathRecreatingMap.put(currentCreatureCoordinate, null);

        while (!queue.isEmpty()) {
            Coordinates currentCoordinate = queue.poll();
            Entity entity = simulationMap.getEntity(currentCoordinate);
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

                addNeighborIfValid(oneStepRightNeighbor, currentCoordinate, queue, pathRecreatingMap, simulationMap, targetCondition);
                addNeighborIfValid(oneStepUpNeighbor, currentCoordinate, queue, pathRecreatingMap, simulationMap, targetCondition);
                addNeighborIfValid(oneStepLeftNeighbor, currentCoordinate, queue, pathRecreatingMap, simulationMap, targetCondition);
                addNeighborIfValid(oneStepDownNeighbor, currentCoordinate, queue, pathRecreatingMap, simulationMap, targetCondition);
            }
        }
        return null;
    }

    private static void addNeighborIfValid(Coordinates neighbor, Coordinates currentCoordinate, Deque<Coordinates> queue,
                                           Map<Coordinates, Coordinates> pathRecreatingMap, SimulationMap simulationMap, Predicate<Entity> targetCondition) {
        if (!pathRecreatingMap.containsKey(neighbor) && canVisit(neighbor, simulationMap, targetCondition)) {
            queue.offer(neighbor);
            pathRecreatingMap.put(neighbor, currentCoordinate);
        }
    }

    private static boolean canVisit(Coordinates neighbor, SimulationMap simulationMap, Predicate<Entity> targetCondition) {
        return (simulationMap.isWithinBounds(neighbor)) && (simulationMap.isEmpty(neighbor) || targetCondition.test(simulationMap.getEntity(neighbor)));
    }
}
