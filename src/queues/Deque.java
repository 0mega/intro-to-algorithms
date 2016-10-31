package queues;

import java.util.Iterator;

/**
 * @author Oleksandr Kruk
 *
 */
public class Deque<Item> implements Iterable<Item> {

  Node<Item> first;

  public Deque() {
    first = null;
  }

  @Override
  public Iterator<Item> iterator() {
    return null;
  }

  public boolean isEmpty() {
    return first == null;
  }

  /**
   * Size of the deque
   * 
   * @return {@code int} number of items on the deque
   */
  public int size() {
    return 0;
  }

  /**
   * 
   * @param item
   *          to be added to Deque
   */
  public void addFirst(Item item) {
    Node<Item> oldFirst = first;
    first = new Node<Item>(item);
    first.next = oldFirst;
  }

  public class Node<T> {
    T value;
    Node<T> next;

    public Node(T v) {
      value = v;
    }
  }
}
