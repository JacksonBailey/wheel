[Behavioral subtyping][1]
=========================

Introduction
------------

For a subtype to behave correctly it doesn't only matter that Java considers it valid (e.g.,
syntax). What also matters is are the specifications. Java doesn't have a way to enforce that so it
needs to be done carefully.

Consider this bad example of Stacks and queues. They both act as Stacks and Queues. Everything below
is perfectly acceptable Java code as far as the JVM is concerned. The problem is that `Queue.put`
does not meet all the specifications of its super, `Stack.put`.

```java
interface Stack<E> {
  /**
   * Put element at the head
   */
  void put(E e);
  /**
   * Return element from the head
   */
  E get();
}

interface Queue<E> extends Stack<E> {
  /**
   * Return element from the tail
   */
  @Override E get();
}
```

Consider this if the problem is not clear. Based on the method signature and the documentation (from
above) we should be able to assume that the assertion in `Caller.main` will pass because the `Stack`
should have standard last in, first out semantics. Because `Queue` (with its first in, first out
semantics) is a subclass it can be returned without problem, but behaviorally there *is* a problem.

```java
class Example {
  Stack<Integer> stack() {
    return new Queue<Integer>();
  }
}

class Caller {
  public static void main(String[] args) {
    var example = new Example();
    Stack<Integer> stack = example.stack();
    stack.put(1);
    stack.put(2);
    assert stack.get() == 2; // Failure
  }
}
```

Specifications
--------------

A type `Sub` is a behavioral subtype of a type `Type` if each behavior allowed by the specification
of `Sub` is also allowed by the specification of `Type`.  More elegantly, for type `Sub` to be a
behavioral subtype of type `Super`, [ref][2]

1. `Sub`'s invariants imply `Super`'s invariants. ***This means stronger invariants.***
2. For each overridden method `m`, `Super.m`'s pre-condition implies `Sub.m`'s pre-condition. This
   means ***weaker pre-conditions***.
3. For each overridden method `m`, `Sub.m`'s post-condition implies `Super.m`'s post-condition. This
   means ***stronger post-conditions***.
    - Perhaps better, `Sub.m`'s post-condition implies `Super.m`'s post-condition ***only when
      `Super.m`'s pre-condition would apply***. This one is confusing, see `OnlyWhenSuperApplies`
      below for an example.

```java
interface Base {
  /**
   * Pre-condition: x >= 0
   * Post-condition: return >= 0
   */
  int work(int x);
}

interface StrongerPostCondition extends Base {
  /**
   * Pre-condition: x >= 0
   * Post-condition: return == |x|
   */
  @Override int work(int x);
}

interface WeakerPreCondition extends Base {
  /**
   * Pre-condition: true (all values supported)
   * Post-condition: return >= 0
   */
  @Override int work(int x);
}

interface OnlyWhenSuperApplies extends Base {
  /**
   * Pre-condition: true (all values supported)
   * Post-condition: return == x
   *
   * The post-condition is weaker but this is still a behavioral subtype. When Base's
   * pre-conditions are true (x >= 0) this post-condition is effectively return >= 0 which implies
   * the supertype's (because it is the same -- but if the effective post-condition were stronger it
   * would still be valid).
   */
  @Override int work(int x);
}
```

Practical examples
------------------

### Modifiable versus unmodifiable

This is an odd one. Just looking at what methods are needed you'd think `Modifiable` is the subtype.
Even without writing a specification you can conceptually see how something `Modifiable` cannot meet
the behaviors of `Unmodifiable`. Then, unless you have "not throwing an exception" as part of your
conditions for `Modifiable` then there's no way for `Unmodifiable` to meet the behaviors of
`Modifiable`. That's a fine thing to do, but it doesn't feel clean. Regardless, we can safely have
the base collections be modifiable and add unmodifiable ones later meeting that requirement. It's
probably best to have a totally different hierarchy though since unmodifiable classes shouldn't even
have a way to modify them (ideally).

### Thread-safe extends not thread-safe

I didn't think super hard about this one because conceptually it is clear that being safe is an
additional requirement over not needing to be safe. Precondition "no other thread is using this" is
stricter.

### Distinct extends indistinct

I didn't think super hard about this one because conceptually it is clear that being distinct is an
additional requirement over not needing to be distinct.

```java
interface Indistinct<E> {

   /**
    * Post-condition: returns true if this[e] >= 1
    */
   boolean contains(E e);

   /**
    * Post-condition: this[e] += 1
    */
   void add(E e);

   /**
    * Post-condition: this[e] = max(this[e] - 1, 0)
    */
   void remove(E e);
}

interface Distinct<E> extends Indistinct<E> {

   /**
    * Post-condition: returns true if this[e] == 1
    */
   boolean contains(E e);

   /**
    * Post-condition: this[e] = 1
    */
   void add(E e);

   /**
    * Post-condition: this[e] = 0
    */
   void remove(E e);
}
```

### Uncapped versus capped

I'm not super sure about these two. I'm sure there's some nitty-gritty detail I'm missing about the
pre-conditions or the exception. If I am then it's probably something to do with the exception. It
kind of makes sense too, because if `Uncapped` can't meet the requirement to throw and someone was
relying on it then you're not correctly using behavioral subtyping.

```java
interface Capped {
  int maxTimes();
  int numTimesThingDone();

   /**
    * Pre-condition: numTimesThingDone() < maxTimes()
    * Post-condition: OLD(numTimesThingDone()) + 1 = numTimesThingDone()
    * Note: OLD(expr) just means value of expr prior to calling this
    */
  void doThing();
}

interface Uncapped extends Capped {

   /**
    * Pre-condition: true
    * Post-condition: (same as above)
    * Bonus points: You could even stop counting once you reach the max
    */
   @Override void doThing();
}
```

If you consider throwing an exception when cap is met as post-condition (which it is) it makes more
sense.

```java
interface Capped {
  int maxTimes();
  int numTimesThingDone();

   /**
    * Pre-condition:  numTimesThingDone() <= maxTimes()
    * Post-condition: if (numTimesThingDone() < maxTimes()) {
    *                   OLD(numTimesThingDone()) + 1 = numTimesThingDone()
    *                 }
    *                 if (numTimesThingDone() == maxTimes() {
    *                   throw Exception
    *                 }
    * Note: OLD(expr) just means value of expr prior to calling this
    */
  void doThing();
}

interface Uncapped extends Capped {

   /**
    * Pre-condition:  true
    * Post-condition: if (numTimesThingDone() < maxTimes()) {
    *                   OLD(numTimesThingDone()) + 1 = numTimesThingDone()
    *                 }
    *                 if (numTimesThingDone() == maxTimes() {
    *                   return // don't throw
    *                 }
    *                 Optionally we can continue counting
    */
   @Override void doThing();
}
```

## Conclusion

1. Base classes will be modifiable, optionally add up to two unmodifiable ones later (one extends
   and one as its own).
2. Base classes will be assumed not thread safe, optionally adding them later
3. Base classes will be indistinct and have distinct versions
4. I'm totally ignoring capped and uncapped because it's confusing. Maybe I'll have an epiphany.

[1]: https://en.wikipedia.org/wiki/Behavioral_subtyping
[2]: https://dr.lib.iastate.edu/entities/publication/a928e154-7899-493f-ad0d-5b81ec34c6e9
