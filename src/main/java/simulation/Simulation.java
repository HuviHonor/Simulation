package simulation;

import action.Action;
import action.InitEntitiesAction;
import action.MoveCreaturesAction;
import action.SpawnEntitiesAction;
import entity.*;
import simulationmap.SimulationMap;
import render.ConsoleRenderer;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final SimulationMap simulationMap;
    private final ConsoleRenderer renderer;
    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();
    private static final int MAP_HEIGHT = 30;
    private static final int MAP_WIDTH = 30;
    private static final int INIT_GRASS_COUNT = 100;
    private static final int MIN_GRASS_COUNT = 25;
    private static final int ROCK_COUNT = 55;
    private static final int TREE_COUNT = 45;
    private static final int INIT_HERBIVORE_COUNT = 50;
    private static final int MIN_HERBIVORE_COUNT = 10;
    private static final int HERBIVORE_SPEED = 1;
    private static final int HERBIVORE_HP = 18;
    private static final int INIT_PREDATOR_COUNT = 10;
    private static final int MIN_PREDATOR_COUNT = 5;
    private static final int PREDATOR_SPEED = 2;
    private static final int PREDATOR_HP = 15;
    private static final int PREDATOR_ATTACK_POWER = 2;
    private volatile boolean isRunning;
    private static final int SLEEP_TIME = 275;
    private int turnCounter;

    public Simulation() {
        this.simulationMap = new SimulationMap(MAP_HEIGHT, MAP_WIDTH);
        initActions.add(new InitEntitiesAction(Grass::new, INIT_GRASS_COUNT));
        initActions.add(new InitEntitiesAction(Rock::new,ROCK_COUNT));
        initActions.add(new InitEntitiesAction(Tree::new,TREE_COUNT));
        initActions.add(new InitEntitiesAction(() -> new Herbivore(HERBIVORE_SPEED, HERBIVORE_HP),INIT_HERBIVORE_COUNT));
        initActions.add(new InitEntitiesAction(() -> new Predator(PREDATOR_SPEED, PREDATOR_HP, PREDATOR_ATTACK_POWER),INIT_PREDATOR_COUNT));
        turnActions.add(new SpawnEntitiesAction<>(Grass.class, MIN_GRASS_COUNT,INIT_GRASS_COUNT,Grass::new));
        turnActions.add(new SpawnEntitiesAction<>(Herbivore.class, MIN_HERBIVORE_COUNT,INIT_HERBIVORE_COUNT,() -> new Herbivore(HERBIVORE_SPEED, HERBIVORE_HP)));
        turnActions.add(new SpawnEntitiesAction<>(Predator.class, MIN_PREDATOR_COUNT,INIT_PREDATOR_COUNT,() -> new Predator(PREDATOR_SPEED, PREDATOR_HP, PREDATOR_ATTACK_POWER)));
        turnActions.add(new MoveCreaturesAction());
        this.renderer = new ConsoleRenderer();
    }

    public void nextTurn() {
        for(Action action : turnActions) {
            action.execute(simulationMap);
        }
        turnCounter++;
        renderer.render(simulationMap, turnCounter);
    }

    public void startSimulation() {
        synchronized (this) {
            for (Action action : initActions) {
                action.execute(simulationMap);
            }
            renderer.render(simulationMap, turnCounter);
            while (true) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                while (isRunning) {
                    nextTurn();
                    sleep();
                }
            }
        }
    }

    public void resumeSimulation() {
        synchronized (this) {
            isRunning = true;
            this.notify();
        }
    }

    public void pauseSimulation() {
        isRunning = false;
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
