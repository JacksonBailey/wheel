No "returns special value" methods

### Walkable

Need to extend iterable and walker so that enhanced for loops work.

```java
interface Walkable<E> extends Iterable<E> {
  default void forEach(Consumer<? super E> action);
  Walker<E> walker();
}

interface Walker<E> extends Iterator<E> {
  default void forEachRemaining(Consumer<? super E> action) {}
  boolean hasNext();
  E next();
  default void remove();
}
```

### Bag

```java
interface Bag<E> extends Walkable<E> {

  // Growing
  boolean add(E e);
  boolean addAll(Bag<? extends E> b);

  // Shrinking
  boolean remove(Object o);
  boolean removeAll(Bag<?> b);
  default boolean removeIf(Predicate<? super E> filter);
  boolean retainAll(Bag<?> b);
  void clear();

  // Looking
  boolean contains(Object o);
  boolean containsAll(Bag<?> b);

  // Sizing
  boolean isEmpty();
  int size();

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  Walker<E> walker();
}
```

### Sequence

Has an order and index

```java
interface Sequence<E> extends Bag<E> {

  // Growing
  void add(int index, E element);
  boolean add(E e);
  boolean addAll(int index, Bag<? extends E> b);
  boolean addAll(Bag<? extends E> b);

  // Shrinking
  E remove(int index);
  boolean remove(Object o);
  boolean removeAll(Bag<?> b);
  boolean retainAll(Bag<?> b);

  // Modifying
  default void replaceAll(UnaryOperator<E> operator);
  E set(int index, E element);
  default void sort(Comparator<? super E> b);

  // Looking
  boolean contains(Object o);
  boolean containsAll(Bag<?> b);
  E get(int index);
  int indexOf(Object o);
  int lastIndexOf(Object o);
  Sequence<E> subSequence(int fromIndex, int toIndex);

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  Walker<E> walker();
  SequenceWalker<E> sequenceWalker();
  SequenceWalker<E> sequenceWalker(int index);

}
```

### Succession

- First in, first out.
- Removes from head
- Adds at tail
-

```java
interface Succession<E> extends Bag<E> {
  boolean add(E e);
  void addLast(E e); // Change return? Change name to addTail?
  E remove();
  E removeFirst();
  E element(); // Add get()?
  E getFirst();
  // Why no walker?
}
```

### Pile

I'm not worried about it yet... but I do want to try to include it.

### Chain

- ~~Does not actually extend stack~~ Because `Stack` is specified to do not necessarily behave like
  a stack (while `ProperStack` does) this can behave how it should.
- Tail is called last
- Head is called first

```java
interface Chain<E> extends Succession<E>, Pile<E> {

  // "From" pile
  void push(E e);
  E pop();

  void addFirst(E e); // Not allowed on "normal" ones... easy to remember because "no cutting in line"
  E removeLast();
  E getLast();

  // Growing
  boolean addAll(Bag<? extends E> b);

  // Shrinking
  boolean remove(Object o);
  boolean removeFirstOccurrence(Object o);
  boolean removeLastOccurrence(Object o);

  // Walking
  Walker<E> descendingWalker();
  Walker<E> walker();

}
```

### Set

TODO Decide if sets will be their own thing or not

```java
interface Set<E> extends Bag<E> {

  // Growing
  boolean add(E e);
  boolean addAll(Bag<? extends E> b);

  // Shrinking
  boolean remove(Object o);
  boolean removeAll(Bag<?> b);
  boolean retainAll(Bag<?> b);

  // Comparing
  boolean equals(Object o);
  int hashCode();

  // Walking
  Walker<E> walker();

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
  // Why no walkers?
}
```

### NavigableSet

```java
interface NavigableSet<E> extends SortedSet<E> {

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

  Walker<E> walker();
  Walker<E> descendingWalker();
  NavigableSet<E> descendingSet();

}
```
