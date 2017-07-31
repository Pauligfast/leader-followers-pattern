package com.iluwatar.leaderfollower;

/**
 * Created by Pashko
 */
public class ConcreteEventHandler implements EventHandler {


    public void handleEvent(Hadle handle, Cab cab) {
       // System.out.println("Cab: " + cab.getId() + " took task");
        Singleton.getInstance().writeToFile("Cab: " + cab.getId() + " took task");
    }
}
