# Discord "server-as-code"

I was thinking recently that Discord servers must have some JSON config representing the entire
config of the server, but it seems like they don't. I thought to myself that it would be nice to
be able to set them up like infrastructure in [Terraform][1]. Thus, this project was born. I put
the title in quotes because "server-as-code" sounds like some sort of "serverless" thing (like
[Lambda][2]), but I mean it in the way Discord uses the term. Which is essentially to refer to a
group of chat rooms. If you know Discord, then you know what a server refers to and I'm not going to
waste my time explaining it lol. That said, I do know that the Discord API calls them "guilds" so
things will probably get a little confusing. (Especially considering [Guilds][3] are a new feature).

## Development

1. To run, `./gradlew :terra:bootRun --args='--spring.profiles.active=live'`
2. To set some default profiles,
   `tasks.named<BootRun>("bootRun") { systemProperty("spring.profiles.active", "live") }`
3. To run with Spring's debug mode, `./gradlew :terra:bootRun --args='--debug'`, [see here][5]
4. Run the jar directly,
   `./gradlew :terra:bootJar && java -jar terra/build/libs/terra.jar --spring.profiles.active=live`

## Notes

I am going to use Spring Boot's [Dependency Management Plugin][4] instead of trying to use the
platform project (`:platform`) in this project. I am doing this because:

1. Many examples of Spring Boot use this plugin so it's easier to follow along.
2. I think I may have really messed something up with the platform project. When I bring it in with
   `implementation(...)` instead of `api(...)` it is also bringing in all the dependencies. I
   vaguely remember this being a problem with the Vexilum project, but it didn't actually break
   anything, but now it's causing problems with logback and the simple-slf4j loggers both being on
   the path.
3. The entire reason I even made the `:platform` project here was because I wanted to see what
   Gradle's own dependency management tools were like in comparison to the plugin Spring Boot
   offers, but it's really only been a hassle and led to me doing even more wacky things. (For
   example, see the mess of `if (requested.id.id == ...)` to try and do this with plugins.) It also
   looks like Gradle may have added more ways to do version catalogs since I originally looked into
   it. I'd like to start over from scratch with it, but I don't want to waste time troubleshooting
   that and lose my muse to work on this project.

## Look into

To reproduce the error below, remove the `versionMapping` section in `java-common-conventions`
plugin. I blindly followed what a link in the error told me without understanding.

```
* What went wrong:
Execution failed for task ':discord:generateMetadataFileForMavenJavaPublication'.
> Invalid publication 'mavenJava':
    - Publication only contains dependencies and/or constraints without a version. You should add
      minimal version information, publish resolved versions (For more on this, please refer to
      https://docs.gradle.org/8.8/userguide/publishing_maven.html#publishing_maven:resolved_dependencies
      in the Gradle documentation.) or reference a platform (For more platforms, please refer to
      https://docs.gradle.org/8.8/userguide/platforms.html in the Gradle documentation.). Disable
      this check by adding 'dependencies-without-versions' to the suppressed validations of the
      :discord:generateMetadataFileForMavenJavaPublication task.
```

[1]: https://www.terraform.io/

[2]: https://aws.amazon.com/lambda/

[3]: https://support.discord.com/hc/en-us/articles/23187611406999-Guilds-FAQ

[4]: https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/

[5]: https://stackoverflow.com/a/71402717/1858327
