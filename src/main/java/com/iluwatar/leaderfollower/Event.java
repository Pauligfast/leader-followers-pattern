package com.iluwatar.leaderfollower;


/**
 * Created by Pashko
 */
public class Event implements Hadle {

  private final int distance;

  Event() {
    this.distance = (short) (Math.random() * 2000);
  }

  public int getDistance() {
    return distance;
  }

}
