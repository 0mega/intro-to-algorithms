package test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

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
  public void itShouldBeEmptyAfterRemovingLastItem() {
    deque.addFirst("testing");
    assertFalse(deque.isEmpty());
    deque.removeFirst();
  }

  @Test
  public void itShouldRemoveInExpectedOrder() {
    deque.addFirst("test");
    deque.addFirst("test2");
    assertEquals(deque.removeFirst(), "test2");
    assertEquals(deque.removeFirst(), "test");
  }

  @Test(expected=NoSuchElementException.class)
  public void itThrowsNoSuchElementWhenRemoveFromEmptyDeque() {
    deque.removeFirst();
  }

  @Test
  public void itShouldAddItemAtTheEndWhenAddLast() {
    deque.addLast("test");
    deque.addLast("test2");
    assertEquals(deque.size(), 2);
    assertEquals(deque.removeFirst(), "test");
    assertEquals(deque.removeFirst(), "test2");
  }

  @Test
  public void itShouldRemoveInFifoOrder() {
    deque.addLast("test");
    deque.addLast("test2");
    assertEquals(deque.size(), 2);
    assertEquals(deque.removeLast(), "test2");
    assertEquals(deque.removeLast(), "test");
  }

  @Test
  public void itShouldUpdateSizeCorrectly() {
    deque.addLast("test");
    deque.addFirst("test2");
    assertEquals(deque.size(), 2);
    deque.removeLast();
    assertEquals(deque.size(), 1);
    deque.removeFirst();
    assertEquals(deque.size(), 0);
  }

  @Test
  public void itShouldWorkCorrectlyWhenAddingAfterEmptyingDeque() {
    deque.addFirst("test");
    deque.removeFirst();
    deque.addFirst("test2");
    assertEquals(deque.removeFirst(), "test2");
  }
}
