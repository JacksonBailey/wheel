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

[annotation-location]: https://docs.oracle.com/javase/specs/jls/se19/html/jls-9.html#jls-9.7.4
