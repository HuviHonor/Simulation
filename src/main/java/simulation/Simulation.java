package simulation;

import action.Action;
import action.InitEntitiesAction;
import action.MoveCreaturesAction;
import action.SpawnEntitiesAction;
import entity.*;
import map.SimulationMap;
import render.ConsoleRenderer;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final SimulationMap map;
    private int turnCounter;
    private final ConsoleRenderer renderer;
    private final List<Action> initActions = new ArrayList<>();
    private final  List<Action> turnActions = new ArrayList<>();
    private static final int MAP_HEIGHT = 50;
    private static final int MAP_WIDTH = 50;
    private static final int MIN_GRASS_COUNT = 100;
    private static final int ROCK_COUNT = 25;
    private static final int TREE_COUNT = 25;
    private static final int MIN_HERBIVORE_COUNT = 20;
    private static final int HERBIVORE_SPEED = 1;
    private static final int HERBIVORE_HP = 10;
    private static final int PREDATOR_COUNT = 5;
    private static final int PREDATOR_SPEED = 2;
    private static final int PREDATOR_HP = 15;
    private static final int PREDATOR_ATTACK_POWER = 2;

    public Simulation() {
        this.map = new SimulationMap(MAP_HEIGHT, MAP_WIDTH);
        initActions.add(new InitEntitiesAction(Grass::new,MIN_GRASS_COUNT));
        initActions.add(new InitEntitiesAction(Rock::new,ROCK_COUNT));
        initActions.add(new InitEntitiesAction(Tree::new,TREE_COUNT));
        initActions.add(new InitEntitiesAction(() -> new Herbivore(HERBIVORE_SPEED, HERBIVORE_HP),MIN_HERBIVORE_COUNT));
        initActions.add(new InitEntitiesAction(() -> new Predator(PREDATOR_SPEED, PREDATOR_HP, PREDATOR_ATTACK_POWER),PREDATOR_COUNT));
        turnActions.add(new SpawnEntitiesAction(Grass.class, MIN_GRASS_COUNT,Grass::new));
        turnActions.add(new SpawnEntitiesAction(Herbivore.class, MIN_HERBIVORE_COUNT,() -> new Herbivore(HERBIVORE_SPEED, HERBIVORE_HP)));
        turnActions.add(new MoveCreaturesAction());
        this.renderer = new ConsoleRenderer();
    }

    public void  nextTurn() throws InterruptedException {
        for(Action action : turnActions) {
            action.execute(map);
        }
        turnCounter++;
        renderer.render(map);
        Thread.sleep(500);
    }

    public void startSimulation() throws InterruptedException {
        for(Action action : initActions) {
            action.execute(map);
        }
        while(true) {
            nextTurn();
        }
    }

    public void pauseSimulation(){

    }
}
