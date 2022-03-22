package bussiness_logic;

import data_models.*;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        if(servers == null){
            return;
        }

        Server bestServer = servers.get(0);
        int minimumWaitingPeriod = servers.get(0).getWaitingPeriod();

        for(Server s : servers){
            if(s.getWaitingPeriod() < minimumWaitingPeriod){
                minimumWaitingPeriod = s.getWaitingPeriod();
                bestServer = s;
            }
        }
        if(bestServer != null){
            bestServer.addTask(task);
        }

    }
}
