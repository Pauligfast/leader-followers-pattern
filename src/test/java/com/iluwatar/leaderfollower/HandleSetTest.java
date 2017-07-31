

package com.iluwatar.leaderfollower;

import org.junit.Assert;
import org.junit.Test;

/**
 * HandleSet class test
 */
public class HandleSetTest {
  @Test
  public void testNewEvent() throws Exception {
    HandleSet handleSet = new HandleSet();
    handleSet.newEvent(new Event());
    Assert.assertTrue(!handleSet.isEmpty());
  }

  @Test
  public void testGetEvent() throws Exception {
    HandleSet handleSet = new HandleSet();
    handleSet.newEvent(new Event());
    Assert.assertTrue(!handleSet.isEmpty());
    handleSet.getEvent();
    Assert.assertTrue(handleSet.isEmpty());
  }
}