import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;


public class Scheduler {

    public static void main(String[] args) {
    	
    	/**
    	 * instantiate array list data structure to hold processes
    	 * 
    	 */

        ArrayList<Proces> process = new ArrayList<>(); //Data Structure to hold processes
        String csvFile = Scheduler.class.getResource("process.csv").getFile(); //
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {  //Read in the input data

            br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] s = line.split(cvsSplitBy);

                process.add(new Proces(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]))); //create new Proces
            }

        } catch (IOException e) {
        }

        compute(process);  
        printBaseInformation();	
        printScheduledProcessList(process);
        printProcessInformationTable(process);
        printProcessSchedulingStatistics(process);

    }

    public static void compute(List<Proces> process) {
        PriorityQueue<Proces> procesQueue = new PriorityQueue<>(process); 
        /**
        what object type is "PriorityQueue" ? what class is this coming from?
         **/
        
        int time = 0;
        while (!procesQueue.isEmpty()) {  
            int minTime = Integer.MAX_VALUE;
            Proces curProces = null;
            for (Proces proces : procesQueue) {
                if (proces.getArrival_Time() <= time) {   
                    curProces = proces;                  
                    curProces.setFinishAndStartTime(time);
                    time += proces.getBurst_Time();
                    break;
                } else if (minTime > proces.getArrival_Time()) minTime = proces.getArrival_Time();
            }
            if (curProces != null) {
                procesQueue.remove(curProces);
            } else time = minTime;
        }

    }

    public static void printScheduledProcessList(List<Proces> process) {
        ArrayList<Proces> p = new ArrayList<>(process); 
        Collections.sort(p, new Comparator<Proces>() {
            @Override // Why do we need override
            public int compare(Proces o1, Proces o2) {
                return o1.getStartTime() - o2.getStartTime();
            }
        });
        System.out.println("Scheduled Process List:");
        //System.out.println();       
        System.out.println("Time  PID");
        for (Proces proces : p) {
            System.out.printf("%2s %6s", proces.getStartTime(), proces.getPID());
            System.out.println();
        }
        System.out.println();
    }

    public static void printProcessInformationTable(List<Proces> process) {
    	System.out.println("Process Information Table");
        System.out.println("PID  Arrival Time   Start Time    Finish TIme   Waiting Time   Turnaround Time");
        for (Proces proces : process) { 	
            System.out.printf("%3s %10s %12s %14s %14s %14s", proces.getPID(), proces.getArrival_Time(), proces.getStartTime(), proces.getFinishTime(), proces.getWaitingTime(), (proces.getWaitingTime() + proces.getBurst_Time()));
            System.out.println();
        }
        System.out.println();
    }

    public static void printProcessSchedulingStatistics(List<Proces> process) {
        int time = 0;
        int sumWaitingTime = 0;
        int sumTurnaroundTime = 0;
        for (Proces proces : process) {
            if (proces.getFinishTime() > time) time = proces.getFinishTime();
            sumWaitingTime += proces.getWaitingTime();
            sumTurnaroundTime += proces.getWaitingTime() + proces.getBurst_Time();
        }
        System.out.println("Total Run Time: " + time);
        System.out.println("Average Wait Time: " + sumWaitingTime / process.size());
        System.out.println("Average Turnaround Time: " + sumTurnaroundTime / process.size());
        if (time!=0) {
            System.out.println("Throughput: " + (double)process.size()/time );
        }
    }

    private static void printBaseInformation() {
    	System.out.println("Name: \t\t\tGideon & Yomi");
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Date:\t\t\t" + dateFormat.format(new Date() ) );
        System.out.println("Scheduling Algorithm:\tShortest Job First (SJF)");
        System.out.println("Input File name is:\tprocess.csv");
        System.out.println();
    }

}
