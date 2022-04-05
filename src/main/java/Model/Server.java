package Model;

import javax.crypto.spec.PSource;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server() {
        tasks = new LinkedBlockingDeque<Task>();
        waitingPeriod = new AtomicInteger(0);
    }

    public synchronized void addTask(Task newTask) {
        tasks.add(newTask);
        int k = waitingPeriod.get();
        waitingPeriod.set(newTask.getServiceTime() + k);

    }

    @Override
    public void run() {
        if (tasks.isEmpty() == false) {
            Task t = tasks.peek();
            int time = t.getServiceTime();
            if (time > 1) {
                waitingPeriod.getAndDecrement();
                time--;
                t.setServiceTime(time);
            } else {
                waitingPeriod.getAndDecrement();
                tasks.remove(t);
                System.out.println("Task " + t.getID() + " has been successfully processed");
            }
        }
    }


    public synchronized AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }
}
