package com.iluwatar.leaderfollower;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Pashko
 */
public class HandleSet {

  private LinkedBlockingQueue<Event> queue = new LinkedBlockingQueue<Event>(100);

  void newEvent(Event input) throws InterruptedException {
    queue.put(input);
  }

  public Event getEvent() throws InterruptedException {
    return queue.take();
  }

  boolean isEmpty() {
    return queue.isEmpty();
  }

}
