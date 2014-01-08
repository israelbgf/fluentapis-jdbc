fluentapis-jdbc
===============

A set of fluent APIs designed to build a small and nice DSL to work with JDBC.

In a nutshell to create a query, you can write a code like this:

```java
List<Object[]> resultList = createQuery("select name, gender from person where age > :age")
                                .on(connection)
								.withValue("age", 18)
								.execute(asList());
```

The development is stil in early stage, so expect some connection leaks and other **severe-high-dangerous** bugs. :P
