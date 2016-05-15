
public class Proces implements Comparable {  
    private int PID;
    private int Arrival_Time;
    private int Burst_Time;
    private int Priority;
    private int startTime;
    private int finishTime;
    private int waitingTime;

    public Proces(int PID,int arrival_Time,  int burst_Time, int priority) {
        Priority = priority;
        this.PID = PID;  
        Arrival_Time = arrival_Time;
        Burst_Time = burst_Time;
    }

    public int getPID() {
        return PID;
    }

    public int getArrival_Time() {
        return Arrival_Time;
    }

    public int getBurst_Time() {
        return Burst_Time;
    }

    public int getPriority() {
        return Priority;
    }

    @Override	
    public int compareTo(Object o) {	
        Proces p = (Proces) o;			
        if (Burst_Time - p.Burst_Time!=0)
        return Burst_Time - p.Burst_Time; else
            return Priority-p.Priority;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishAndStartTime(int startTime) {
        finishTime = startTime + Burst_Time;
        this.startTime = startTime;
        waitingTime = startTime - Arrival_Time;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
