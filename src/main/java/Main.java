import simulation.Simulation;
import ui.SimulationUI;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        SimulationUI simulationUI = new SimulationUI();
        Thread startSimulationThread = new Thread(simulation::startSimulation);
        Thread simulationUIThread = new Thread(() -> simulationUI.print(simulation));
        startSimulationThread.setDaemon(true);
        startSimulationThread.start();
        simulationUIThread.start();
    }
}