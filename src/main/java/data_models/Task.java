package data_models;

public class Task implements Comparable{

    private int arrivalTime;
    private int serviceTime;

    private int waitingTime;
    private int clientID;

    public Task(int arrivalTime, int serviceTime, int clientID){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
        this.clientID = clientID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(arrivalTime, ((Task)o).getArrivalTime());
    }

    @Override
    public String toString() {
        return "(" + this.clientID + "," + this.arrivalTime + "," + this.serviceTime + ")";
    }

}
