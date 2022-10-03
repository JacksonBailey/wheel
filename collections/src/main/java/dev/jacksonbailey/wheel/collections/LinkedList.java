package dev.jacksonbailey.wheel.collections;

/**
 * A simple implementation of a linked list. Included to show how multiple modules work within
 * Gradle.
 */
public class LinkedList {

  private Node head;

  /**
   * Adds an element to the end of the list.
   *
   * @param element the element to add
   */
  public void add(String element) {
    Node newNode = new Node(element);

    Node it = tail(head);
    if (it == null) {
      head = newNode;
    } else {
      it.next = newNode;
    }
  }

  private static Node tail(Node head) {
    Node it;

    for (it = head; it != null && it.next != null; it = it.next) {
    }

    return it;
  }

  /**
   * Removes the first element of the list that matches according to
   * {@link String#compareTo(String)}. Returns true if such an element is removed and false
   * otherwise.
   *
   * @param element the element to remove from the list
   * @return true if an element is removed; false otherwise
   */
  public boolean remove(String element) {
    boolean result = false;
    Node previousIt = null;
    Node it = null;
    for (it = head; !result && it != null; previousIt = it, it = it.next) {
      if (0 == element.compareTo(it.data)) {
        result = true;
        unlink(previousIt, it);
        break;
      }
    }

    return result;
  }

  private void unlink(Node previousIt, Node currentIt) {
    if (currentIt == head) {
      head = currentIt.next;
    } else {
      previousIt.next = currentIt.next;
    }
  }

  /**
   * Returns the size of the list.
   *
   * @return the size of the list
   */
  public int size() {
    int size = 0;

    for (Node it = head; it != null; ++size, it = it.next) {
    }

    return size;
  }

  /**
   * Returns the nth element of the list. The index is 0-based.
   *
   * @param index the index of the element to return
   * @return the nth element of the list
   * @throws IndexOutOfBoundsException if the index is out of range
   *                                   ({@code index < 0 || index > size()})
   */
  public String get(int index) {
    Node it = head;
    while (index > 0 && it != null) {
      it = it.next;
      index--;
    }

    if (it == null) {
      throw new IndexOutOfBoundsException("Index is out of range");
    }

    return it.data;
  }

  private static class Node {

    final String data;
    Node next;

    Node(String data) {
      this.data = data;
    }
  }
}
