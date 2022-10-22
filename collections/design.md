No "returns special value" methods

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
