# Collections

This specific project focuses on collections.

- Goals:
    - Learn about API design
    - Make something us*able*
    - Make use of new Java features (e.g., `Optional`)
    - Mirror Java where appropriate
    - Don't duplicate names
- Non goals:
    - Making something use*ful*
    - Adding spliterators, arrays, and streams
    - Making implementations from scratch -- wrappers around existing things is fine
    - Good names -- Things like `Iterable2` are fine
    - Adding the "functional" methods (like `removeIf`)

## The standard collections API

For reference.

- Iterable -> Collection
  - List
  - Queue -> Deque
  - Set -> SortedSet -> NavigableSet
- Map -> SortedMap -> NavigableMap -v
  - ConcurrentMap -> ConcurrentNavigableMap

## Design

- Do not allow nulls into the collection
- Return `Optional` for nullable things
- Avoid throwing exceptions if possible

## Reminder

When trying to remember how the Collections API uses the terms head and tail imagine an array of a
snake whose head is at zero and as it eats it tail gets longer and longer.

## Hierarchy

### Iterable

```java
interface Iterable<E> {
  default void forEach(Consumer<? super E> action);
  Iterator<E> iterator();
  default Spliterator<E> spliterator();
}

interface Iterator<E> {
  default void forEachRemaining(Consumer<? super E> action) {}
  boolean hasNext();
  E next();
  default void remove();
}
```

### Collection

```java
interface Collection<E> extends Iterable<E> {

  // Growing
  boolean add(E e);
  boolean addAll(Collection<? extends E> c);

  // Shrinking
  boolean remove(Object o);
  boolean removeAll(Collection<?> c);
  default boolean removeIf(Predicate<? super E> filter);
  boolean retainAll(Collection<?> c);
  void clear();

  // Looking
  boolean contains(Object o);
  boolean containsAll(Collection<?> c);

  // Sizing
  boolean isEmpty();
  int size();

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  @Override Iterator<E> iterator();
  @Override default Spliterator<E> spliterator();
  default Stream<E> stream();
  default Stream<E> parallelStream();

  // Array-ing
  Object[] toArray(); // Not great
  default <T> T[] toArray(IntFunction<T[]> generator);
  <T> T[] toArray(T[] a);
}
```

### List

Has an order and index

```java
interface List<E> extends Collection<E> {

  // Growing
  void add(int index, E element);
  boolean add(E e);
  boolean addAll(int index, Collection<? extends E> c);
  boolean addAll(Collection<? extends E> c);

  // Shrinking
  void clear();
  E remove(int index);
  boolean remove(Object o);
  boolean removeAll(Collection<?> c);
  boolean retainAll(Collection<?> c);

  // Modifying
  default void replaceAll(UnaryOperator<E> operator);
  E set(int index, E element);
  default void sort(Comparator<? super E> c);

  // Looking
  boolean contains(Object o);
  boolean containsAll(Collection<?> c);
  E get(int index);
  int indexOf(Object o);
  int lastIndexOf(Object o);
  List<E> subList(int fromIndex, int toIndex);

  // Sizing
  boolean isEmpty();
  int size();

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  Iterator<E> iterator();
  ListIterator<E> listIterator();
  ListIterator<E> listIterator(int index);
  default Spliterator<E> spliterator();

  // Array-ing
  Object[] toArray();
  <T> T[] toArray(T[] a);

}
```

### Queue

- First in, first out.
- Removes from head
- Adds at tail

| Operation  | Throws exception | Special return |
|------------|------------------|----------------|
| Insert     | `add(e)`         | `offer(e)`     |
| Remove     | `remove()`       | `poll()`       |
| Examine    | `element()`      | `peek()`       |

```java
interface Queue<E> extends Collection<E> {
  boolean add(E e);
  E remove();
  E element();
  boolean offer(E e);
  E poll();
  E peek();
}
```

### Deque

- Does not actually extend stack
- Tail is called last
- Head is called first

```java
interface Deque<E> extends Queue<E> {

  // From queue
  boolean add(E e);
  E remove();
  E element(); // Odd ball... No get()
  boolean offer(E e);
  E poll();
  E peek();

  // "From" stack
  void push(E e);
  E pop();

  // Queue like
  void addLast(E e);
  boolean offerLast(E e);
  E removeFirst();
  E pollFirst();
  E getFirst();
  E peekFirst();

  // Stack like
  void addFirst(E e);
  boolean offerFirst(E e);
  E removeLast();
  E pollLast();
  E getLast();
  E peekLast();

  // Growing
  boolean addAll(Collection<? extends E> c);

  // Shrinking
  boolean remove(Object o);
  boolean removeFirstOccurrence(Object o);
  boolean removeLastOccurrence(Object o);

  // Looking
  boolean contains(Object o);

  // Sizing
  int size();

  // Walking
  Iterator<E> descendingIterator();
  Iterator<E> iterator();

}
```

### Set

```java
interface Set<E> extends Collection<E> {

  // Growing
  boolean add(E e);
  boolean addAll(Collection<? extends E> c);

  // Shrinking
  boolean remove(Object o);
  boolean removeAll(Collection<?> c);
  boolean retainAll(Collection<?> c);
  void clear();

  // Looking
  boolean contains(Object o);
  boolean containsAll(Collection<?> c);

  // Sizing
  boolean isEmpty();
  int size();

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  Iterator<E> iterator();
  default Spliterator<E> spliterator();

  // Array-ing
  Object[] toArray();
  <T> T[] toArray(T[] a);

}
```

### SortedSet

- head returns `..., to)`
- sub returns `[from, to)`
- tail returns `[from, ...`

```java
interface SortedSet<E> extends Set<E> {
  Comparator<? super E> comparator();
  E first();
  E last();
  SortedSet<E> headSet(E toElement);
  SortedSet<E> subSet(E fromElement, E toElement);
  SortedSet<E> tailSet(E fromElement);
  default Spliterator<E> spliterator();
}
```

### NavigableSet

```java
interface NavigableSet<E> extends SortedSet<E> {

  E pollFirst();
  E pollLast();

  E ceiling(E e);
  E floor(E e);
  E higher(E e);
  E lower(E e);

  SortedSet<E> headSet(E toElement);
  NavigableSet<E> headSet(E toElement, boolean inclusive);
  NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive);
  SortedSet<E> subSet(E fromElement, E toElement);
  SortedSet<E> tailSet(E fromElement);
  NavigableSet<E> tailSet(E fromElement, boolean inclusive);

  Iterator<E> iterator();
  Iterator<E> descendingIterator();
  NavigableSet<E> descendingSet();

}
```
