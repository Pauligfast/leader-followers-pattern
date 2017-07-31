package com.iluwatar.leaderfollower;

import java.util.List;

public class Cab implements Runnable {

    private final HandleSet handleSet;
    private List<Cab> cabs;
    private final int id;
    private final ThreadPool threadPool;
    private final ConcreteEventHandler concreteEventHandler;

    /**
     * Cab constructor
     */
    Cab(HandleSet handleSet, List<Cab> cabs, int id, ThreadPool threadPool,
        ConcreteEventHandler concreteEventHandler) {
        this.handleSet = handleSet;
        this.cabs = cabs;
        this.id = id;
        this.threadPool = threadPool;
        this.concreteEventHandler = concreteEventHandler;
    }


    /**
     * Promotes a new Leader and notifies all the cabs
     */
    private void becomeLeader(Cab cab) {
        synchronized (threadPool) {
            threadPool.notifyAll();
        }
        threadPool.promoteNewLeader(cab);
    }

    int getId() {
        return id;
    }

    /**
     * Method is called when thread is executed.
     * Makes Leader thread to listen to input and handle evens. Followers are waiting to get promoted.
     **/
    public void run() {
        while (!handleSet.isEmpty()) {
            try {
                if (threadPool.getLeader() != this && threadPool.getLeader() != null) {
                    synchronized (threadPool) {
                        threadPool.wait();
                    }
                }
                cabs.remove(this);
                //System.out.println("Cab: " + id + " became a leader");
                Singleton.getInstance().writeToFile("Cab: " + id + " became a leader");

                if (!cabs.isEmpty()) {
                    becomeLeader(threadPool.getCabs().get(0));

                } else {
                    becomeLeader(null);
                }

                Event event = handleSet.getEvent();
                concreteEventHandler.handleEvent(event, this);

                java.lang.Thread.sleep(event.getDistance());
               // System.out.println("Cab: " + id + " completed the task. Travelled " + event.getDistance() + " miles");
                Singleton.getInstance().writeToFile("Cab: " + id + " completed the task. Travelled " + event.getDistance() + " miles");
                threadPool.addThread(this);

            } catch (InterruptedException e) {
                System.out.println("interrupted");
                return;
            }
        }
    }
}
