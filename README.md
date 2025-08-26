REST Assured workshop
==================
For those of you looking to gain some experience working with [REST Assured](http://rest-assured.io/), here are all the materials from a workshop I've created and delivered multiple times to good reviews. Feel free to use, share and adapt these materials as you see fit.

What do I need?
---
A Java 21 JDK, Maven and an IDE of your choice.

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

