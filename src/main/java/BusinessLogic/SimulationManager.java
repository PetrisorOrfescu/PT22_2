package BusinessLogic;

import Model.Server;
import Model.Task;
import View.SimulationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public class SimulationManager implements Runnable {

    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;
    public Scheduler scheduler;
    private List<Task> generatedTasks;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int minArrivalTime, int maxArrivalTime, int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.selectionPolicy = selectionPolicy;
        scheduler = new Scheduler(this.numberOfServers, this.selectionPolicy);

    }


    private synchronized void generateNRandomTasks() {
        generatedTasks = Collections.synchronizedList(new ArrayList<Task>());
        for (int i = 1; i <= numberOfClients; i++) {
            Random rand = new Random();
            int arrivalTime = rand.nextInt(abs(maxArrivalTime - minArrivalTime)) + minArrivalTime;
            int serviceTime = rand.nextInt(abs(maxProcessingTime - minProcessingTime)) + minProcessingTime;
            Task t = new Task(i, arrivalTime, serviceTime);
            System.out.println("Task(" + t.getID() + "," + t.getArrivalTime() + "," + t.getServiceTime() + ") has been successfully created");
            generatedTasks.add(t);
        }
        Collections.sort(generatedTasks);

    }

    @Override
    public void run() {
        File file = new File("C:\\Users\\Petrisor\\IdeaProjects\\TP-TEMA2\\logs.txt");
        try {
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        generateNRandomTasks();
        double sumService = 0;
        for (Task t : generatedTasks) {
            sumService += t.getServiceTime();
        }
        int currentTime = 0;
        while (currentTime <= timeLimit) {
            System.out.println("Current time: " + currentTime);
            for (Server s : scheduler.getServers()) {
                Thread thr = new Thread(s);
                thr.start();
            }

            for (Task t : generatedTasks) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                }
            }
            currentTime++;
        }


        System.out.println("Average Waiting Time: " + sumService / numberOfClients);

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
