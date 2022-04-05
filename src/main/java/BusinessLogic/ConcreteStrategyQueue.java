package BusinessLogic;

import Model.Server;
import Model.Task;


import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public synchronized void addTask(List<Server> servers, Task task) {
        int minlength = 10000;
        for (Server s : servers) {
            if (s.getTasks() != null && s.getTasks().size() < minlength)
                minlength = s.getTasks().size();
        }
        int i = 0;
        for (Server s : servers) {
            i++;
            if (s.getTasks() != null && s.getTasks().size() == minlength) {
                s.addTask(task);
                System.out.println("Task(" + task.getID() + "," + task.getArrivalTime() + "," + task.getServiceTime() + ") has been successfully assigned to server no:" + i);
                break;
            }
        }

    }
}
