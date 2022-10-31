<style> a { text-decoration: underline; } </style>

# Lessons learned

I thought it would be fun to keep track of things I've learned.

## Annotations, why `@NotNull` shows on the type and method in Javadoc

Annotations that are both `ElementType.TYPE_USE` and `METHOD` Are both a *declaration annotation*
and a *type annotation*. When an annotation is on the method I thought it made a difference if it
was next to the return type or before the modifiers. It does not matter where it is. Both of the
uses of annoations below (which are `TYPE_USE` and `METHOD`) apply to both the method as a whole and
to the return type of the method. If you look at the Javadoc generated from such an example you'll
see `@NotNull` mentioned twice.

This is confusing. By the [JLS][annotation-location] it is fine (and makes sense) but the IntelliJ
Annotations themselves are a little odd because that are `@Documented`. If they were not
`@Documented` then would not appear in the Javadoc.

This happes for method parameters as well.

TODO: Can we make a child annotation that is more narrow to avoid this that IntelliJ still
recognizes as being `@NotNull`?

```java
public class Example {
  public @NotNull String foo() { return "foo"; }
  @NotNull public String bar() { return "bar"; }
}
```

## `Cloneable` and `clone()`, why `shallowCopy()` is not pointless

I was under the impression that using the [`clone()`][clone-javadoc] was similar to using Java's
serializing framework in that it was a bit of a no-no. For a brief moment I thought that was wrong,
but after some research I found that this intuition is correct. See "Item 13: Override `clone`
judiciously" of Effective Java for more in-depth information. The main problems are that

1. Every value is copied so this means it doesn't make new objects.
2. You can recursively call `clone()` on mutable things to get around this but it means the fields
   must be non-final.

The documentation called this a shallow copy. My understanding was shallow copy implied new objects
but not new objects in those objects. I may be using the term wrong. As in deep copy goes all the
way down and shallow goes one layer -- but it seems like the docs used it to mean *no* layers down
apart from the wrapper (well, the class) itself.

## Adding [raw HTML][raw-html] to CommonMark

See the raw Markdown and above you'll see `<style> a { text-decoration: underline; } </style>`. I
added this because I like to have links to Javadoc be monospaced, but it doesn't show it as a link.
This makes (all) links be underlined.

[annotation-location]: https://docs.oracle.com/javase/specs/jls/se19/html/jls-9.html#jls-9.7.4
[clone-javadoc]: https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Object.html#clone()
[cloneable-javadoc]: https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Cloneable.html
[raw-html]: https://spec.commonmark.org/0.30/#raw-html
