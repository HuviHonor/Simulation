package action;

import entity.Creature;
import entity.Entity;
import simulationmap.SimulationMap;

import java.util.List;

public class MoveCreaturesAction implements Action {
    @Override
    public void execute(SimulationMap simulationMap) {
        List<Entity> entities = simulationMap.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Creature creature && simulationMap.containsEntity(creature)) {
                creature.makeMove(simulationMap);
            }
        }
    }
}
