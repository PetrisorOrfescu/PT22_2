package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    //private int maxNoServers;
    //private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, SelectionPolicy strategyEnum) {
        servers = Collections.synchronizedList(new ArrayList<Server>());
        changeStrategy(strategyEnum);
        for (int i = 0; i < maxNoServers; i++) {
            Server s = new Server();
            servers.add(s);
        }
    }

    public synchronized void dispatchTask(Task t) {
        strategy.addTask(servers, t);
    }

    public synchronized void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public synchronized List<Server> getServers() {
        return servers;
    }
}

