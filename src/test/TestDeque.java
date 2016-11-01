package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import queues.Deque;

public class TestDeque {

  private Deque<String> deque;

  @Before
  public void setup() {
    deque = new Deque<String>();
  }

  @Test
  public void itShouldBeEmptyAfterCreated() {
    assertTrue(deque.isEmpty());
  }

  @Test
  public void itShouldNotBeEmptyAfterAddingString() {
    deque.addFirst("testing");
    assertFalse(deque.isEmpty());
  }

  @Test
  public void itShouldBeEmptyAfterAddingAndRemovingOneItem() {
    deque.addFirst("testing");
    assertFalse(deque.isEmpty());
    deque.removeFirst();
  }
}
