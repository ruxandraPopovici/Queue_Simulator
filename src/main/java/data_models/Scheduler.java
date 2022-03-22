package data_models;

import bussiness_logic.ConcreteStrategyQueue;
import bussiness_logic.ConcreteStrategyTime;
import bussiness_logic.SelectionPolicy;
import bussiness_logic.Strategy;

import java.util.*;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        this.strategy = new ConcreteStrategyTime();

        servers = new ArrayList<Server>();

        for(int i = 0; i < maxNoServers; i++){
            //ID pentru fiecare coada
            Server server = new Server(maxTasksPerServer, i + 1);
            servers.add(server);
            //creaza thread
            (new Thread(server)).start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    //daca nu mai avem cozi active
    //returneaza false daca nu mai sunt cozi active
    public boolean activeQueues(){
        boolean active = false;
        for(Server s : servers){
            if(s.getTasks().size() != 0){
                active = true;
                break;
            }
        }
        return active;
    }

    //decrementam serviceTime pentru primul task de la fiecare coada, deci incrementam timpul de asteptare
    //pentru toate task-urile cozii respective
    public void decrementServiceTime(){
        for(Server s : servers){
            if(!s.getTasks().isEmpty()){
                s.getFirstTask().setServiceTime(s.getFirstTask().getServiceTime() - 1);

                for(Task task: s.getTasks()) {
                    task.setWaitingTime(task.getWaitingTime() + 1);
                }

            }
        }
    }
    public void updateWaitingTime() {
        for (Server server: servers) {
            server.updateWaitingTime();
        }
    }
    public void incrementWaitedTime() {
        for (Server server: servers) {
            if(server.getTasks() != null) {
                server.setWaitedTime(server.getWaitedTime() + server.getTasks().size());
            }
        }
    }
    public int getTotalWaitedTime() {
        int waitedTime = 0;
        for (Server server: servers) {
            waitedTime += server.getWaitedTime();
        }
        return waitedTime;
    }

    public void dispatchTask(Task task){
        strategy.addTask(servers, task);
    }

    public void stop(){
        for (Server server: servers) {
            server.stop();
        }
    }

    public List<Server> getServers(){
        return servers;
    }

}
