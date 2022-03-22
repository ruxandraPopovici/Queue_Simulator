package bussiness_logic;

import data_models.Server;
import data_models.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        if(servers == null){
            return;
        }

        Server bestServer = servers.get(0);
        int minimumQueueLength = servers.get(0).getTasks().size();

        for(Server s : servers){
            if(s.getTasks().size() < minimumQueueLength){
                minimumQueueLength = s.getTasks().size();
                bestServer = s;
            }
        }
        if(bestServer != null){
            bestServer.addTask(task);
        }


    }
}
