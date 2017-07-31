package com.iluwatar.leaderfollower;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Pashko
 */
class ThreadPool {

  private final int numberofThreads;
  private Cab leader;
  private List<Cab> cabs = Collections.synchronizedList(new LinkedList<Cab>());
  private ExecutorService executorService = Executors.newFixedThreadPool(30);
  private ConcreteEventHandler concreteEventHandler = new ConcreteEventHandler();
  private HandleSet handleSet = new HandleSet();


  /**
   * ThreadPool constructor
   */
  ThreadPool(ExecutorService executorService, int numberfEvents, int numberofThreads)
      throws InterruptedException {

    this.executorService = executorService;
    this.numberofThreads = numberofThreads;
    for (int i = 0; i < numberfEvents; i++) {
      handleSet.newEvent(new Event());
    }
  }

  /**
   * Creates and executes cabs
   */
  void start() throws InterruptedException {

    for (int i = 1; i <= numberofThreads; i++) {
      Cab cab = new Cab(handleSet, cabs, i, this, concreteEventHandler);
      cabs.add(cab);
    }

    promoteNewLeader(cabs.get(0));

    for (int i = 0; i < numberofThreads; i++) {
      executorService.submit(cabs.get(i));
    }
  }

  Cab getLeader() {
    return this.leader;
  }

  void promoteNewLeader(Cab leader) {
    this.leader = leader;
  }

  /**
   * Adds new cab to the List
   */
  void addThread(Cab cab) {

    cabs.add(cab);

  }

  List<Cab> getCabs() {
    return cabs;
  }
}
