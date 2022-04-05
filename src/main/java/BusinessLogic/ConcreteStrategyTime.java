package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    @Override
    public synchronized void addTask(List<Server> servers, Task task) {
        int minTime = 10000;
        for(Server s : servers)
        {
            if(s.getWaitingPeriod().get() < minTime)
                minTime = s.getWaitingPeriod().get();
        }
        int i=0;
        for(Server s : servers)
        {
            i++;
            if(s.getWaitingPeriod().get() == minTime) {
                s.addTask(task);
                System.out.println("Task("+task.getID()+","+task.getArrivalTime()+","+task.getServiceTime()+") has been successfully assigned to server no:"+i);
                i=0;
                break;
            }
        }
    }
}
