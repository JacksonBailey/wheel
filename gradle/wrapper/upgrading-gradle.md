Upgrading Gradle
================

From [here][upgrading]

> Projects will typically want to keep up with the times and upgrade their
> Gradle version to benefit from new features and improvements. One way to
> upgrade the Gradle version is manually change the `distributionUrl` property
> in the Wrapper’s `gradle-wrapper.properties` file. The better and recommended
> option is to run the `wrapper` task and provide the target Gradle version as
> described in [Adding the Gradle Wrapper][adding]. Using the `wrapper` task
> ensures that any optimizations made to the Wrapper shell script or batch file
> with that specific Gradle version are applied to the project. As usual, you
> should commit the changes to the Wrapper files to version control.

> Note that running the wrapper task once will update
> `gradle-wrapper.properties` only, but leave the wrapper itself in
> `gradle-wrapper.jar` untouched. This is usually fine as new versions of Gradle
> can be run even with ancient wrapper files. If you nevertheless want all the
> wrapper files to be completely up-to-date, you’ll need to run the wrapper task
> a second time.

> Use the Gradle `wrapper` task to generate the wrapper, specifying a version.
> The default is the current version. Once you have upgraded the wrapper, you
> can check that it’s the version you expect by executing `./gradlew --version`.

In conclusion, run the `wrapper` task twice!

Script
------

You need `jq` to run this. Use your package manager of choice to install it or on Git-for-Windows do
`curl -L -o /usr/bin/jq.exe https://github.com/stedolan/jq/releases/latest/download/jq-win64.exe`.

The below code will upgrade the wrapper and the wrapper's init scripts (`gradlew`).

```shell
newest_gradle_url="https://services.gradle.org/versions/current"
# Change current to release-candidate if you want the RCs.
newest_gradle_version="$(curl --silent ${newest_gradle_url} | jq --raw-output '.version')"
newest_gradle_sha256_sum="$(curl --silent --location \
    $(curl --silent ${newest_gradle_url} | jq --raw-output '.checksumUrl'))"

./gradlew wrapper --gradle-version "${newest_gradle_version}" \
               --distribution-type bin \
               --gradle-distribution-sha256-sum "${newest_gradle_sha256_sum}" \
               --no-build-cache \
    && \
./gradlew wrapper --gradle-version "${newest_gradle_version}" \
               --distribution-type bin \
               --gradle-distribution-sha256-sum "${newest_gradle_sha256_sum}" \
               --no-build-cache
```

Verifying
---------

For some reason this is not part of the above but it should be. ***(As an aside,
it is extremely important to verify the Gradle Wrapper JAR from untrusted
repos.)***

```shell
pushd gradle/wrapper

curl --silent --location --output gradle-wrapper.jar.sha256 \
    "$(curl --silent ${newest_gradle_url} | jq --raw-output '.wrapperChecksumUrl')"

echo "  gradle-wrapper.jar" >> gradle-wrapper.jar.sha256
sha256sum --check gradle-wrapper.jar.sha256 # shasum on macOS
# Output should be "gradle-wrapper.jar: OK"

rm gradle-wrapper.jar.sha256 # Keeping this provides no benefit as an attacker could modify it too

popd
```

[upgrading]: https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:upgrading_wrapper
[adding]: https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:adding_wrapper
[verifying]: https://docs.gradle.org/current/userguide/gradle_wrapper.html#wrapper_checksum_verification
