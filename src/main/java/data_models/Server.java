package data_models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private int queueID;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod; //suma timpilor de procesare a clientilor
    private boolean running = true;
    private int waitedTime;

    public Server(int maxNoTasks, int ID) {
        this.queueID = ID;
        this.tasks = new ArrayBlockingQueue<Task>(maxNoTasks);
        this.waitingPeriod = new AtomicInteger(0);
        this.waitedTime = 0;
    }

    public BlockingQueue<Task> getTasks(){
        return this.tasks;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.intValue();
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while(running){
            try{
                if(tasks.isEmpty()){
                    Thread.sleep(1000);
                }
                else{
                    Task task = tasks.peek();
                    int time = (task.getServiceTime() - 1)* 1000;
                    Thread.sleep(time);

                    tasks.take();
                    if(!tasks.isEmpty()) {
                        task = tasks.peek();
                        task.setServiceTime(task.getServiceTime() + 1);
                    }
                    Thread.sleep(1000);
                }
            }
            catch(InterruptedException exception){
                System.out.println("Process suddenly interrupted!");
            }
        }

    }

    public int getWaitedTime() {
        return waitedTime;
    }
    public void setWaitedTime(int waitedTime) {
        this.waitedTime = waitedTime;
    }

    public Task getFirstTask(){
        if(tasks.isEmpty()){
            return null;
        }

        Task[] taskArray = new Task[tasks.size()];
        int i = 0;
        for(Task t : tasks){
            taskArray[i] = t;
            i++;
        }
        return taskArray[0];
    }

    public void stop() {
        this.running = false;
    }

    public void updateWaitingTime() {
        waitingPeriod.getAndDecrement();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Queue " + queueID + ": ");
        if (tasks.isEmpty()) return string + "closed.";
        for (Task task : tasks) {
            string.append(task);
        }
        return string.toString();
    }

}
