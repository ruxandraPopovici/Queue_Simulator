package data_models;

import bussiness_logic.SimulationManager;

public class QueueModel {

    private SimulationManager manager;

    public QueueModel(){ }
    public void setInformation(int maxSimulation, int[] arrival, int[] service, int noServers, int noClients){
        manager = new SimulationManager(maxSimulation, arrival[0], arrival[1],
                        service[0], service[1], noServers, noClients);

        manager.generateNRandomTasks();
        Thread simManager = new Thread(manager);
        simManager.start();
    }
}
