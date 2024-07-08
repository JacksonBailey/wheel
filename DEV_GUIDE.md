Development guide
=================

A guide for common development tasks.

Gradle task help
----------------

Gradle has built in help, but it is easy to forget how to use it. It is not always fully documented
but it is a nice first place to check. (Especially if you remember it exists.)

```shell
./gradlew help --task assemble
```

In addition we use the `com.dorongold.task-tree` plugin to show a tree view of tasks when needed.
Gradle makes a big deal about tasks forming a directed acyclic graph but for whatever reason does
not have a way to see that. This is super useful for seeing exactly what happens during certain
tasks like `assemble` or `check`. Just include the task `taskTree` and instead of executing anything
Gradle will output the tree.

```shell
./gradlew taskTree assemble
```

Upgrading versions
------------------

To assist with this, place links near versions to `mvnrepository.com` to quickly check for updates.
The tools are sometimes finicky and it is nice to have a quick way to check.

We use `com.github.ben-manes.versions` plugin to check for dependency updates. It should be set up
correctly to only report new stable releases. I prefer manually upgrading versions when running this
as opposed to an automated approach.

```shell
./gradlew dependencyUpdates
```

To upgrade the version of the Gradle wrapper please see [this][upgrade-gradle] document.

[upgrade-gradle]: gradle/wrapper/upgrading-gradle.md

Checking dependency scopes and transitivity
-------------------------------------------

We use `com.autonomousapps.dependency-analysis` to check the scope of dependencies. This makes sure
that if we are using something as `implementation` but we only need it as `runtimeOnly` that we can
minimize it. This also makes sure we explicitly depend on all dependencies we directly use rather
than relying on them transitively.

```shell
./gradlew projectHealth
# The tool's documentation says to use buildHealth but it does not automatically print
# ./gradlew buildHealth && cat build/reports/dependency-analysis/build-health-report.txt
```

You can drill down with `reason` on a specific project and dependency like so.

```shell
./gradlew :vexillum:vexillum-core:reason --id org.junit.jupiter:junit-jupiter-api
```

Planning future tasks
---------------------

This project uses `TODO` comments to indicate potential future tasks.
