package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import queues.RandomizedQueue;

public class TestRandomizedQueue {

  RandomizedQueue<String> queue;
  
  @Before
  public void setup() {
    queue = new RandomizedQueue<String>();
  }
  
  @Test
  public void itShouldCreateRandomizedQueue() {
    assertNotNull(queue);
  }
  
  @Test
  public void itShouldBeEmptyAfterCreated() {
    assertTrue(queue.isEmpty());
  }

}
