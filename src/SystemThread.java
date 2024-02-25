import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class SystemThread extends Thread{
    private Queue<Road> queue = new LinkedList<>();
    private int numberOfRoads;
    private int numberOfIntervals;
    private int seconds = 0;
    private Timer timer;
    private volatile boolean isRunning = true;


    public void setSystemOpen(Boolean systemOpen) {
        isSystemOpen = systemOpen;
    }

    private Boolean isSystemOpen = false;

    SystemThread(String threadName, int numberOfRoads, int numberOfIntervals) {
        super(threadName);
        this.numberOfRoads = numberOfRoads;
        this.numberOfIntervals = numberOfIntervals;
    }

    @Override
    public void run() {
        do {
            seconds++;
            if (isSystemOpen) {
                System.out.printf("""
                        ! %ds. have passed since system startup !
                        ! Number of roads: %d !
                        ! Interval: %d !
                        """, seconds, numberOfRoads, numberOfIntervals);

                Iterator<Road> iterator = queue.iterator();
                while (iterator.hasNext()) {
                    Road item = iterator.next();
                    System.out.print(item);
                    item.setSeconds(item.getSeconds() - 1);
                }

                System.out.println("! Press \"Enter\" to open menu !");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while(isRunning && !Thread.currentThread().isInterrupted());
    }

    public void setRunning(boolean running) {
        isRunning = running;
        this.interrupt();
    }

    public void addRoad(String roadName) {
        if (queue.size() < numberOfRoads) {
            if (queue.size() == 0) {
                queue.add(
                    new Road(
                            roadName,
                    true,
                            numberOfIntervals,
                            numberOfIntervals
                            )
                );
            } else {
                Road lastRoad = null;
                for (Road road : queue) {
                    lastRoad = road;
                }
                queue.add(
                    new Road(
                            roadName,
                            false,
                             queue.size() * numberOfIntervals,
                            numberOfIntervals
                    )
                );
            }
            System.out.println(roadName + " Added!");
        } else {
            System.out.println("queue is full");
        }
    }

    public void deleteRoad() {
        if (queue.isEmpty()) {
            System.out.println("queue is empty");
        } else {
            Road road = queue.remove();
            System.out.println(road.getName() + " deleted!");
        }
    }

}
