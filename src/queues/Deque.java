package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Oleksandr Kruk
 *
 */
public class Deque<Item> implements Iterable<Item> {

  Node<Item> first;
  Node<Item> last;
  int size;

  public Deque() {
    first = null;
    last = first;
  }

  @Override
  public Iterator<Item> iterator() {
    return new ListIterator(first);
  }

  /**
   * 
   * @return {@code true} if {@link Deque} is empty, {@code false} otherwise
   */
  public boolean isEmpty() {
    return first == null;
  }

  /**
   * Size of the deque
   * 
   * @return {@code int} number of items on the deque
   */
  public int size() {
    return size;
  }

  /**
   * 
   * @param item
   *          to be added to Deque
   */
  public void addFirst(Item item) {
    if (isEmpty()) {
      first = last =  new Node<Item>(item);
    } else {
      Node<Item> oldFirst = first;
      first = new Node<Item>(item);
      first.next = oldFirst;
      oldFirst.previous = first;
    }

    size++;
  }

  public Item removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException("deque is empty");
    Item toReturn = first.value;

    if (size == 1) {
      first = last = null;
    } else {
      Node<Item> oldFirst = first;
      first = oldFirst.next;
      oldFirst.next = null;
      first.previous = null;
    }

    size--;
    return toReturn;
  }

  public void addLast(Item item) {
    if (isEmpty()) {
      first = last = new Node<Item>(item);
    } else {
      Node<Item> oldLast = last;
      last = new Node<Item>(item);
      last.previous = oldLast;
      oldLast.next = last;
    }

    size++;
  }

  public Item removeLast() {
    if (isEmpty())
      throw new NoSuchElementException("deque is empty");

    final Item toReturn = last.value;
    if(size == 1) {
      first = last = null;
    } else {
      Node<Item> oldLast = last;
      last = oldLast.previous;
      oldLast.previous = null;
      last.next = null;
    }

    size--;
    return toReturn;
  }

  public class Node<T> {
    public T value;
    public Node<T> next;
    public Node<T> previous;

    public Node(T v) {
      value = v;
    }
  }

  public class ListIterator implements Iterator<Item> {

    Node<Item> current;

    public ListIterator(Node<Item> firstNode) {
      current = firstNode;
    }

    @Override
    public boolean hasNext() {
      return current.next != null;
    }

    @Override
    public Item next() {
      if (current.next == null)
        throw new NoSuchElementException();

      return current.next.value;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
