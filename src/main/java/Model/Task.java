package Model;

public class Task implements Comparable {
    private final int ID;
    private final int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    @Override
    public synchronized String toString() {
        return "Task{" +
                "ID=" + ID +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }

    public synchronized int getID() {
        return ID;
    }

    public synchronized int getArrivalTime() {
        return arrivalTime;
    }

    public synchronized int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public synchronized int compareTo(Object o) {
        int compareage = (((Task) o).getArrivalTime());
        return (this.getArrivalTime() - compareage);
    }
}
