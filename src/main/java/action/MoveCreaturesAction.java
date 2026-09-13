package action;

import entity.Creature;
import simulationmap.SimulationMap;
import navigation.Coordinates;
import java.util.List;

public class MoveCreaturesAction implements Action {
    @Override
    public void execute(SimulationMap simulationMap) {
        List<Coordinates> creatureCoordinates = simulationMap.getAllCreaturesCoordinates();
        for (Coordinates nextCreatureCoordinates : creatureCoordinates) {
            Creature nextCreature = (Creature) simulationMap.getEntity(nextCreatureCoordinates);
            if (nextCreature == null) {
                continue;
            }
            nextCreature.makeMove(nextCreatureCoordinates, simulationMap);
        }
    }
}
