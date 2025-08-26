
- i found this project, forked it n now im practicting REST Assured. Java 21, Maven 
  




What API is used for the exercises?
---
All API calls that are used in the examples and exercises have been mocked using [WireMock](http://wiremock.org/). WireMock is included in this project as a dependency, so there's no need for additional setup.

Running the mock server
---
The mock server used to respond to the API calls you're making in the exercises is started and stopped automatically using the `@WireMockTest` annotation.

Running the tests using Maven
---

```bash
mvn clean test
```

