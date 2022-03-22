package bussiness_logic;

import data_models.Server;
import data_models.Task;

import java.util.*;

public interface Strategy {
    public void addTask(List<Server> servers, Task task);
}
