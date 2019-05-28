# spring-utils
Some convenience utils for Spring Framework.

[![Build Status](https://travis-ci.com/kburger/spring-utils.svg?branch=develop)](https://travis-ci.com/kburger/spring-utils)
[![Coverage Status](https://coveralls.io/repos/github/kburger/spring-utils/badge.svg?branch=develop)](https://coveralls.io/github/kburger/spring-utils?branch=develop)
[![Dependabot Status](https://api.dependabot.com/badges/status?host=github&repo=kburger/spring-utils)](https://dependabot.com)

## Quickstart
Add the `spring-utils` artifact to the POM's `project.dependencies` section.
```xml
<dependency>
    <groupId>com.github.kburger</groupId>
    <artifactId>spring-utils</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

### [Conditionals](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Conditional.html)
```java
/** Only instantiate this bean if there is no preexisting bean in the context. */
@Bean
@Condition(ExistingBeanCondition.class)
public MyBean myBean() {
    return new MyBean();
}
```

## Changelog
See [CHANGELOG.md](CHANGELOG.md) for a [list of notable changes for each version](https://keepachangelog.com/en/1.0.0/#what).

## Development
This project follows the [git-flow](https://nvie.com/posts/a-successful-git-branching-model/) principles. For the latest development activities, see the [`develop`](/../../tree/develop) branch.

## Versioning
This project uses semantic versioning. See [semver.org](https://semver.org/) for more information.

## License
This project is licensed under the [MIT license](LICENSE).
