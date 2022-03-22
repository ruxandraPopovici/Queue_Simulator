package bussiness_logic;

import data_models.*;

import java.io.*;
import java.util.*;

public class SimulationManager implements Runnable{
    private int timeLimit = 60;
    private int maxProcessingTime = 4;
    private int minProcessingTime = 2;
    private int maxArrivalTime = 30;
    private int minArrivalTime = 2;
    private int numberOfServers = 2;
    private int numberOfClients = 4;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private List<Task> generatedTasks;

    private File file;
    private String fileName = "logOfEvents.txt";

    public SimulationManager(){
        Task client1 = new Task(2, 2, 1);
        Task client2 = new Task(3, 3, 2);
        Task client3 = new Task(4, 3, 3);
        Task client4 = new Task(10, 2, 4);
        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfClients);

        this.generatedTasks = new ArrayList<>(numberOfClients);

        this.generatedTasks.add(client1);
        this.generatedTasks.add(client2);
        this.generatedTasks.add(client3);
        this.generatedTasks.add(client4);

        Collections.sort(generatedTasks);
    }

    public SimulationManager(int timeLimit, int minArrivalTime, int maxArrivalTime,
                             int minProcessingTime, int maxProcessingTime, int noServers, int noClients){
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;

        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;

        this.numberOfServers = noServers;
        this.numberOfClients = noClients;

        this.scheduler = new Scheduler(noServers, noClients);
        this.selectionPolicy = SelectionPolicy.SHORTEST_TIME;

        try{
            file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        }catch(IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
        }
    }

    public void generateNRandomTasks() {
        this.generatedTasks = new ArrayList<>(numberOfClients);
        Random rand = new Random();
        int procTime, arrTime;
        Task task;

        for (int i = 0; i < numberOfClients; i++) {
            //minimul + un numar intre cele doua
            procTime = this.minProcessingTime + rand.nextInt(this.maxProcessingTime - this.minProcessingTime + 1);
            arrTime = this.minArrivalTime + rand.nextInt(this.maxArrivalTime - this.minArrivalTime + 1);
            task = new Task(arrTime, procTime, i + 1);
            this.generatedTasks.add(task);
        }
        //sortare in functie de arrTime:
        Collections.sort(generatedTasks);
    }

    @Override
    public void run() {
        String fileText = "";

        int peak = 0;
        int peakHour = 0;

        int currentTime = 0;
        float waitedTime = 0;

        while(currentTime <= timeLimit){
            System.out.println("Time: " + currentTime);
            fileText += "Time: " + currentTime + "\n";

            if(!generatedTasks.isEmpty()){

                Task currentTask = generatedTasks.get(0);
                while( currentTime == currentTask.getArrivalTime()){
                    scheduler.dispatchTask(currentTask);
                    generatedTasks.remove(0);
                    if(generatedTasks.isEmpty()){
                        break;
                    }
                    currentTask = generatedTasks.get(0);
                }

                System.out.println("Waiting clients: ");
                fileText += "Waiting clients: ";
                for(Task t : generatedTasks){
                    System.out.print(t.toString() + "; ");
                    fileText += t.toString() + "  ";
                }
                System.out.println();
                fileText += "\n";
            }
            int newPeak = 0;

            for(Server queue : scheduler.getServers()){
                System.out.println(queue.toString());
                fileText += queue.toString() + "\n";
                newPeak += queue.getTasks().size();
            }

            System.out.println();

            if(newPeak > peak){
                peak = newPeak;
                peakHour = currentTime;
            }
            fileText += "\n";

            scheduler.updateWaitingTime();
            currentTime++;
            scheduler.incrementWaitedTime();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            scheduler.decrementServiceTime();

            if(generatedTasks.isEmpty() && !scheduler.activeQueues()){
                waitedTime = (float)scheduler.getTotalWaitedTime() / numberOfClients;
                break;
            }
        }
        scheduler.stop();
        System.out.println("Done.");
        System.out.println("Average waiting time: " + waitedTime);
        System.out.println("Peak hour: " + peakHour);
        fileText += "Average waiting time: " + waitedTime + "\n";
        fileText += "Peak hour: " + peakHour;

        addToFile(fileName, fileText);
    }

    public void printTasks(){
        for(Task t : generatedTasks){
            System.out.println(t);
        }
    }

    public void addToFile(String file, String text){
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
