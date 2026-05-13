package action;

import entity.Creature;
import map.SimulationMap;
import navigation.Coordinates;
import java.util.List;

public class MoveCreaturesAction implements Action {
    @Override
    public void execute(SimulationMap map) {
        List<Coordinates> creatureCoordinates = map.getAllCreaturesCoordinates();
        for (Coordinates nextCreatureCoordinates : creatureCoordinates) {
            Creature nextCreature = (Creature) map.getEntity(nextCreatureCoordinates);
            if (nextCreature == null) {
                continue;
            }
            nextCreature.makeMove(nextCreatureCoordinates, map);
        }
    }
}
