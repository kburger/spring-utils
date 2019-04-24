# spring-utils
Some convenience utils for Spring Framework

[![Build Status](https://travis-ci.com/kburger/spring-utils.svg?branch=develop)](https://travis-ci.com/kburger/spring-utils)

## Quickstart
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
