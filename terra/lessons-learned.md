<style> a { text-decoration: underline; } </style>

# Lessons learned

I thought it would be fun to keep track of things I've learned.

## Spring Builders and Customizers

A lot of times you don't necessarily want to create one specific object, like a `WebClient`.

```java

@Bean
public WebClient webClient() {
  return new WebClient();
}
```

So you might instead make a builder, like `WebClient.Builder`. You might also make this a prototype
scope bean so that everywhere you request one you'll get a new builder. If you still want a
`WebClient` as a bean then you'll still end up with just the one bean. This also means anyone
messing with the builder won't affect others using it.

```java

@Bean
@Scope("prototype")
public WebClient.Builder webClientBuilder() {
  return new WebClient.builder();
}

@Bean
public WebClient webClient(WebClient.Builder webClientBuilder) {
  return webClientBuilder.build();
}

@Bean
public FooService fooService(WebClient.Builder webClientBuilder) {
  // Even if this FooService messes with the builder, it's a different one than above because
  // of prototype scope.
  return new FooService(webClientBuilder);
}
```

But what if we *do* want to mess with it? What if we want people to be able to customize the "base
builder"? Well, if we control the code making it then it's easy. But what if we don't? Like in the
case of writing libraries used by others (or using libraries written by others)?

I knew Spring did this, so I just dug in and checked the `WebClientAutoConfiguration`. It's a little
counterintuitive. You can use `ObjectProvider<T>`. Spring will make it for you with all instances of
`T`. A visual example is better.

```java

@FunctionalInterface
public interface WebClientCustomizer {

  void customize(WebClient.Builder builder);
}
```

And in your config,

```java

@Bean
@Scope("prototype")
public WebClient.Builder webClientBuilder(ObjectProvider<WebClientCustomizer> customizerProvider) {
  WebClient.Builder builder = WebClient.Builder();
  customizerProvider.orderedStream().forEach((cutomizer) -> customizer.customize(builder));
  return builder;
}

@Bean
public WebClientCustomizer blahCustomizer() {
  return (builder) -> builder.blah();
}

@Bean
public WebClientCustomizer barCustomizer(BarService barService) {
  return (builder) -> builder.addService(barService);
}

@Bean
public FooService fooService(WebClient.Builder webClientBuilder) {
  // This webClientBuilder will have `blah()` called as well as `barService` in the services
  return new FooService(webClientBuilder);
}
```

So, that's how you can customize the "base builder" from `AutoConfigurartion`s.

In addition, you can "scan" for all implementations of a type (that are beans at least).

```java

@Bean
@Scope("prototype")
public JDABuilder jdaBuilder(ObjectProvider<ListenerAdapter> listenerProvider) {
  JDABuilder builder = new JDABuilder();
  listenerProvider.orderedStream().forEach(builder::addEventListeners);
  return builder;
}
```

## Semantic Line Breaks

I installed the Grazie Pro plugin. (Very confusingly named, it has a few features even without a
subscription.) It suggests "structural line breaks" and points to
[Semantic Line Breaks](https://sembr.org/). It's interesting. It suggests wrapping text lines in
things like Markdown at the end of sentences/clauses/thoughts. I'll consider it but will likely
pass.
