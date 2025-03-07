# Liberty-MicroProfile-REST-Service-Example

This project is a simple RESTful API built with [IBM WebSphere Liberty](https://www.ibm.com/cloud/watson-studio) and Jakarta EE/MicroProfile. It demonstrates how to create a lightweight REST service using MicroProfile JAX-RS. The project is packaged as a WAR file and built with Gradle. Integration tests are written using Spock (Groovy) and the Java HTTP client, making it a great reference for beginner programmers.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Project Setup and Creation](#project-setup-and-creation)
- [Installation and Running the Application](#installation-and-running-the-application)
- [Endpoints](#endpoints)
- [Package Configuration](#package-configuration)
- [Testing](#testing)
- [Code Explanation](#code-explanation)
- [Dependencies](#dependencies)
- [Summary](#summary)

---

## Project Overview

**Liberty-MicroProfile-REST-Service-Example** is designed as a beginner-friendly reference project to demonstrate how to build RESTful APIs using Liberty with Jakarta EE and MicroProfile. The project covers:
- Creating a Liberty project using Gradle and the Liberty Gradle plugin.
- Configuring the application with MicroProfile features.
- Defining REST endpoints for GET (with path and query parameters) and POST (with JSON request bodies).
- Writing integration tests using Spock for direct method invocation and using Java’s HttpClient for full endpoint testing.
- Documenting the code using Javadoc for better maintainability and clarity.

---

## Project Setup and Creation

You can create your Liberty project in one of the following ways:

### Option 1: Using the Liberty Online Generator
1. Visit the [Liberty Application Generator](https://developer.ibm.com/wasdev/) or similar online tool.
2. Fill in the project details (for example, set the Group as `dim.kal` and Artifact as `rest-service-example`).
3. Choose **Gradle** as the build system and include Jakarta EE and MicroProfile extensions.
4. Download the generated project ZIP file and extract it to your project folder.

### Option 2: Manual Setup
1. **Create a New Project Folder:**  
   Create a folder for your project and navigate into it.
2. **Initialize a Gradle Project:**  
   Use your IDE or run:
   ```bash
   gradle init --type basic
   ```

3. **Add the Liberty Plugin and Dependencies:**
Update your `build.gradle` file with the Liberty plugin and provided dependencies (see Package Configuration below).

4. **Configure the Project Structure:**
Place your source code under `src/main/java`, your configuration files under `src/main/resources`, and your tests under `src/test/groovy`.

---

## Endpoints
 ### Root Endpoint
- **Path:** `/`
- **Method:** `GET`
- **Description:** Returns a static greeting message.
- **Response Example:**
```json
{
"msg": "Hello World!",
}
```
### Path Parameter Greeting
- **Path:** `/greet/{name}`
- **Method:** `GET`
- **Description:** Returns a greeting that includes the provided name.
- **Response Example:**
```json
{
"msg": "Hello Harry!",
}
```
### Query Parameter Greeting
- **Path:** `/greeting`
- **Method:** `GET`
- **Description:** Returns a personalized greeting using name and age query parameters. Returns HTTP 400 if parameters are missing.
- **Response Example:**
```json
{
"msg": "Hello my name is John and i'm 30 years old!",
}
```
### Request Body Greeting
- **Path:** `/greeting`
- **Method:** `POST`
- **Description:** Accepts a JSON request body containing name and age and returns a personalized greeting. Returns HTTP 400 for invalid request bodies.
Response Example:
```json
{
"msg": "Hello my name is Bob Trench and i'm 25 years old!",
}
```
## Package Configuration
### Build Configuration (build.gradle)

```gradle
plugins {
    id'java'
    id 'war'
    id 'io.openliberty.tools.gradle.Liberty' version '3.9.2'
    id 'groovy'
}

version '1.0-SNAPSHOT'
group 'dim.kal'

sourceCompatibility = 17
targetCompatibility = 17
tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}

repositories {
    mavenCentral()
}

dependencies {
    // provided dependencies
    providedCompile 'jakarta.platform:jakarta.jakartaee-api:10.0.0' 
    providedCompile 'org.eclipse.microprofile:microprofile:7.0'
    testImplementation 'org.spockframework:spock-core:2.0-groovy-3.0'
    testImplementation 'org.codehaus.groovy:groovy-all:3.0.8'
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    testImplementation 'org.glassfish.jersey.core:jersey-common:3.1.2'
    testImplementation 'org.glassfish.jersey.core:jersey-server:3.1.2'
    testImplementation 'org.glassfish.jersey.inject:jersey-hk2:3.1.2'
    testImplementation 'org.eclipse:yasson:2.0.1'

}

clean.dependsOn 'libertyStop'

tasks.named('test'){
    useJUnitPlatform()
}

```
1. **Plugins:**

The `plugins` block defines which Gradle plugins to use:
- `id 'java'`: This plugin applies the basic Java plugin to your project, enabling standard Java compilation and testing tasks.
- `id 'war'`: This plugin is used to build a WAR (Web Application Archive) file, which is typically used for Java web applications.
- `id 'io.openliberty.tools.gradle.Liberty' version '3.9.2'`: This plugin integrates the IBM Open Liberty application server with Gradle. It allows you to start, stop, and configure Liberty tasks from Gradle. Version 3.9.2 is specified here.
- `id 'groovy'`: This plugin adds support for the Groovy programming language. It's used for writing Groovy-based tests (e.g., Spock framework) in this project.

2. **Version and Group:**
- `version '1.0-SNAPSHOT'`: Specifies the current version of the project. The SNAPSHOT means this is a development version that may change.
- `group 'dim.kal'`: Defines the group ID (organization or project namespace) for the project.

3. **Source and Target Compatibility:**
- `sourceCompatibility = 17`: Specifies the Java source compatibility version (Java 17 here). This means the code is compiled as if it was written for Java 17.
targetCompatibility = 17: Specifies the version of the JVM bytecode that will be generated (also Java 17).

4. **Java Compilation Options**:
```gradle
tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}
```
This section sets the encoding for the Java source files to `UTF-8`.

5. **Repositories:**

```gradle
repositories {
    mavenCentral()
}
```
Defines the repositories from which to fetch dependencies. Here, it specifies Maven Central, which is a public repository hosting many commonly used Java libraries.

6. **Dependencies:**

    Dependencies define the libraries that are required for the project.

- `Provided Dependencies`:

    - `providedCompile 'jakarta.platform:jakarta.jakartaee-api:10.0.0'`: The Jakarta EE API is provided by the Liberty server, so it's marked as a "provided" dependency. This means it will not be packaged in the WAR file since Liberty already provides it at runtime.
    - `providedCompile 'org.eclipse.microprofile:microprofile:7.0'`: The MicroProfile API is also provided by Liberty, so it's similarly marked as provided.
- `Test Dependencies`:

    - `testImplementation 'org.spockframework:spock-core:2.0-groovy-3.0'`: Adds Spock as the testing framework, which is Groovy-based. Spock is used for writing tests with a more expressive syntax.
    - `testImplementation 'org.codehaus.groovy:groovy-all:3.0.8'`: Adds Groovy, which is the language used for Spock-based tests.
    - `testImplementation 'org.junit.jupiter:junit-jupiter'`: JUnit Jupiter is the new generation of JUnit (JUnit 5) and is used for unit tests.
    - `testRuntimeOnly 'org.junit.platform:junit-platform-launcher'`: JUnit Platform is required for running tests in the JUnit 5 ecosystem.
    - `testImplementation 'org.glassfish.jersey.core:jersey-common:3.1.2'`: Adds Jersey libraries for working with RESTful web services.
    - `testImplementation 'org.glassfish.jersey.core:jersey-server:3.1.2'`: Adds server-side functionality for testing REST APIs.
    - `testImplementation 'org.glassfish.jersey.inject:jersey-hk2:3.1.2'`: Provides dependency injection for Jersey (part of the Jersey server).
    - `testImplementation 'org.eclipse:yasson:2.0.1'`: Adds Yasson, which is the JSON-B (Jakarta JSON Binding) implementation used by Jersey.

7. **Clean Task Dependency:**

    ```gradle
    clean.dependsOn 'libertyStop'
    ```
    This line ensures that when the clean task is executed, it will first stop the Liberty server if it's running. This is helpful when you're cleaning the build and want to ensure the server is stopped before proceeding.

8. **Test Task Configuration:**

    ```gradle
    tasks.named('test') {
        useJUnitPlatform()
    }
    ```

    This configures the test task to use JUnit Platform, which is the foundation for running JUnit 5 tests. It enables running both JUnit 5 and Spock tests.
---

### Key Points:
- **The project is set up to be packaged as a WAR file** to run in an application server, specifically IBM Open Liberty, which provides Jakarta EE and MicroProfile.

- **Dependencies are managed** carefully, with runtime dependencies marked as "provided" (like Jakarta EE and MicroProfile) to avoid including them in the WAR file, since Liberty provides them.

- **Testing** is configured with the Spock framework for Groovy-based tests and JUnit 5 for traditional Java tests.

- **The Liberty Gradle plugin** integrates with the IBM Open Liberty application server for starting, stopping, and deploying the application.




## Testing
The project includes integration tests written with Spock (Groovy) that verify both direct controller invocation and full endpoint integration via HTTP requests. The tests use a fluent "given-when-then" syntax and logging for debugging.

### Running Tests

From the project root, run:

```bash
./gradlew clean test
```
### Example Test Scenarios
- **Direct Controller Test (RestResourceSpec):**
Tests the controller methods directly (unit-style) using Spock. It verifies the response status and message for `GET` requests (both root and with path parameters), query parameter requests, and `POST` requests with a JSON body.
Test class has been renamed to `RestResourceSpec` for clarity.

- **HTTP Integration Test (IntegrationResourceController):**
Tests the deployed endpoints by sending HTTP requests via Java’s `HttpClient`. This verifies that the Liberty server is running and handling requests correctly.
Test class has been renamed to `IntegrationRestResourceSpec` for clarity.

---

## Code Explanation

- **REST Controller (RestController.java)**

```java
package dim.kal.rest.resourceControllers;


import dim.kal.rest.models.Greeting;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/")
public class RestController {

    public RestController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        return Response.ok(new Greeting("Hello World!")).build();
    }

    @GET
    @Path("greet/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pathParamsGreeting(@PathParam("name") String name) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new Greeting("Name or age can't be null"))
                           .build();
        }
        return Response.ok(new Greeting("Hello " + name + "!")).build();
    }

    @GET
    @Path("/greet")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryParamsGreeting(@QueryParam("name") String name, @QueryParam("age") Integer age) {
        if (name == null || age == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new Greeting("Name or age can't be null"))
                           .build();
        }
        return Response.ok(new Greeting("Hello my name is: " + name + " and my age is: " + age + " old!!")).build();
    }

    @POST
    @Path("/greet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bodyParams(Greeting input) {
        if (input.getName() == null || input.getAge() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new Greeting("Name or age can't be null"))
                           .build();
        }
        return Response.ok(new Greeting("Hello my name is: " + input.getName() + " and my age is: " + input.getAge() + " old!!")).build();
    }
}
```

This controller defines REST endpoints using `Jakarta EE/JAX-RS` annotations. It returns a `Greeting` object, which is automatically serialized to JSON. Each method handles specific scenarios, including missing parameters (returning HTTP 400).


- **Greeting.java**

```java
package dim.kal.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Greeting {
private String msg;
private String name;
private Integer age;

    public Greeting() {
    }

    public Greeting(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```

The Greeting model represents the JSON response. The `@JsonInclude` annotation omits null values, so only initialized fields appear in the output.

### Testing Code Explanation

- **Direct Controller Tests (RestResourceSpec.groovy)**

```groovy
package dim.kal.rest.resourceControllers

import dim.kal.rest.models.Greeting
import dim.kal.rest.resourceControllers.RestController
import spock.lang.Specification

/**
* Direct unit tests for the REST controller.
  */
  class RestResourceSpec extends Specification {

  RestController controller

  void setup() {
  controller = new RestController()
  }

  def "test GET endpoint returns Hello World!"() {
  when:
  def response = controller.helloWorld()

       then:
       response.getStatus() == 200
       def message = response.getEntity()
       message.getMsg() == "Hello World!"
  }

  def "test path params greeting method"() {
  when:
  def response = controller.pathParamsGreeting(name)

       then:
       response.getStatus() == status
       response.getEntity().getMsg() == msg

       where:
       name         || status || msg
       "Dimitris"  || 200    || "Hello Dimitris!"
       "John"      || 200    || "Hello John!"
       null        || 400    || "Name or age can't be null"
  }

  def "test query params greeting method"() {
  when:
  def response = controller.queryParamsGreeting(name, age)

       then:
       response.getStatus() == status
       response.getEntity().getMsg() == msg

       where:
       name   || age || status || msg
       "Dim"  || 25  || 200    || "Hello my name is: Dim and my age is: 25 old!!"
       null   || 30  || 400    || "Name or age can't be null"
       "Tom"  || null|| 400    || "Name or age can't be null"
  }

  def "test body params greeting method"() {
  given:
  def input = new Greeting()
  input.setName(name)
  input.setAge(age)
  when:
  def response = controller.bodyParams(input)

       then:
       response.getStatus() == status
       response.getEntity().getMsg() == msg

       where:
       name   || age || status || msg
       "Dim"  || 25  || 200    || "Hello my name is: Dim and my age is: 25 old!!"
       null   || 30  || 400    || "Name or age can't be null"
       "Tom"  || null|| 400    || "Name or age can't be null"
  }
  }
  ```
  This Spock test class (renamed to RestResourceSpec for clarity) tests the controller methods directly by invoking them and asserting on the returned Response object.

- **HTTP Integration Tests (IntegrationRestResourceSpec.groovy)**
```groovy

package dim.kal.rest.resourceControllers

import dim.kal.rest.models.Greeting
import groovy.json.JsonSlurper
import spock.lang.Shared
import spock.lang.Specification

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
* Integration tests for the REST service using HTTP requests.
  */
  class IntegrationRestResourceSpec extends Specification {

  static final String LIBERTY_REST_EXAMPLE_URL = "http://localhost:9080/rest-service-example/api/"

  @Shared
  HttpClient client

  @Shared
  JsonSlurper jsonSlurper = new JsonSlurper()

  def setup() {
  client = HttpClient.newHttpClient()
  }

  def "200: successfully submit a GET request to the root endpoint"() {
  given:
  def request = HttpRequest.newBuilder()
  .uri(URI.create(LIBERTY_REST_EXAMPLE_URL))
  .header("Content-Type", "application/json")
  .GET()
  .build()

       when:
       def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

       then:
       jsonResponse.statusCode() == 200
       def responseBody = jsonSlurper.parseText(jsonResponse.body())
       responseBody.msg == "Hello World!"
  }

  def "200: successfully submit a GET request with path parameters"() {
  given:
  def name = "John"
  def request = HttpRequest.newBuilder()
  .uri(URI.create(LIBERTY_REST_EXAMPLE_URL + "greet/" + name))
  .header("Content-Type", "application/json")
  .GET()
  .build()

       when:
       def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

       then:
       jsonResponse.statusCode() == 200
       def responseBody = jsonSlurper.parseText(jsonResponse.body())
       responseBody.msg == "Hello " + name + "!"
  }

  def "200: successfully submit a GET request with query parameters"() {
  given:
  def name = "Xantakias"
  def age = 33
  def request = HttpRequest.newBuilder()
  .uri(URI.create(LIBERTY_REST_EXAMPLE_URL + "greet?name=" + name + "&age=" + age))
  .header("Content-Type", "application/json")
  .GET()
  .build()

       when:
       def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

       then:
       jsonResponse.statusCode() == 200
       def responseBody = jsonSlurper.parseText(jsonResponse.body())
       responseBody.msg == "Hello my name is: " + name + " and my age is: " + age + " old!!"
  }

  def "200: successfully submit a POST request with a valid JSON body"() {
  given:
  def bodyReq = new Greeting()
  bodyReq.setName("Xantakias")
  bodyReq.setAge(33)
  def jsonData = new groovy.json.JsonBuilder(bodyReq).toPrettyString()

       def request = HttpRequest.newBuilder()
               .uri(URI.create(LIBERTY_REST_EXAMPLE_URL + "greet"))
               .header("Content-Type", "application/json")
               .POST(HttpRequest.BodyPublishers.ofString(jsonData))
               .build()

       when:
       def jsonResponse = client.send(request, HttpResponse.BodyHandlers.ofString())

       then:
       jsonResponse.statusCode() == 200
       def responseBody = jsonSlurper.parseText(jsonResponse.body())
       responseBody.msg == "Hello my name is: " + bodyReq.getName() + " and my age is: " + bodyReq.getAge() + " old!!"
  }
  }
  ```
  This integration test class (renamed to IntegrationRestResourceSpec) uses Java’s `HttpClient` and Groovy’s `JsonSlurper` to send actual HTTP requests to the Liberty server. It verifies responses for GET requests (both with and without parameters) and for `POST`requests with a JSON body.

---

## Dependencies
### Key dependencies used in this project:

 - **Jakarta EE/MicroProfile APIs:**
    - `jakarta.platform:jakarta.jakartaee-api:10.0.0` – Provides Jakarta EE APIs for building enterprise applications.
    - `org.eclipse.microprofile:microprofile:7.0` – Supports MicroProfile features for cloud-native Java applications.
- **Spock Framework (Testing):**
    - `org.spockframework:spock-core:2.0-groovy-3.0` – A powerful testing framework for Java/Groovy applications.
    - `org.codehaus.groovy:groovy-all:3.0.8` – Required for Groovy-based Spock tests.
- **JUnit 5 (Unit Testing):**
    - `org.junit.jupiter:junit-jupiter` – JUnit 5 API for writing and running tests.
    - `org.junit.platform:junit-platform-launcher` – Enables running JUnit tests on different platforms.
- **Jersey (JAX-RS Support & Testing):**
    - `org.glassfish.jersey.core:jersey-common:3.1.2` – Provides core components for Jersey-based JAX-RS applications.
    - `org.glassfish.jersey.core:jersey-server:3.1.2` – Supports server-side JAX-RS implementations.
    - `org.glassfish.jersey.inject:jersey-hk2:3.1.2` – Provides dependency injection support with HK2 for Jersey.
- **Yasson (JSON Processing):**
    - `org.eclipse:yasson:2.0.1` – A reference implementation of JSON-B for converting Java objects to JSON and vice versa.

    ---

 ## Summary
   This project demonstrates how to build a RESTful API using `IBM WebSphere Liberty`, `Jakarta EE`, and `MicroProfile`. It includes:
- A structured `Gradle-based project` with Jakarta EE and MicroProfile features.
- A `REST API` with endpoints for handling `GET` and `POST` requests using `path parameters`, `query parameters`, and `JSON request bodies`.
- `Unit and integration tests` using `Spock` and Java’s `HttpClient`.
- A well-organized `build configuration` with the Liberty Gradle plugin for automated deployment.
