
# Spring Boot MicroBadge Instructions

## What is Spring Boot?
Spring Boot is a framework designed to simplify the bootstrapping of a web application.
The framework can free the developer from defining boilerplate configuration. 
Spring boot favors convention over configuration and makes it easy to quickly create stand-alone, production-grade, Spring based applications that "just run".

Spring Boot has excellent online documentation and guides at [https://projects.spring.io/spring-boot/]()

## What You'll Need!
* Time: 3 or more hours
* Knowledge:
  * Fluency with Java language, including annotations
  * Familiarity with GitHub
  * Familiarity with Maven
  * Familiarity with the IntelliJ IDE
  * Familiarity with Docker
  * Familiarity with terminal window/command line file system navigation and tools.
    * This microbadge has been tested using MacOS and Linux.
* Software:
  * [JDK 17](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
  * [Maven 3.0+](https://maven.apache.org/download.cgi)
  * [IntelliJ IDE](https://www.jetbrains.com/idea/)

## Check Your Tools!
To begin, we will jump-start by creating an initial maven project with a Spring Boot web site tool.  This step will validate you have all the necessary tools to run a Spring Boot application on your machine.
* Using a browser, go to: http://start.spring.io/
* Add "Web" and "Actuator" to the dependencies list
  * Do this by searching for "Web" and add it to the selected dependencies.
  * Then search for "Actuator" and add it to the selected dependencies.
* Leave the other fields in their default state (or modify them to fit your personal preferences)
* Select "Generate Project" - it will download a zip file for you.
* Extract the zip file contents into a location where you want the project to reside, and open the project (pom.xml) using IntelliJ
* Run the Application
  * INTELLIJ: Run the application from IntelliJ (find "DemoApplication.java", right click and run)
    * You have successfully run the DemoApplication if the last line of text within the console window shows: 
      * "Started DemoApplication in n.nnn seconds (JVM running for x.xxx)"
    * To stop the application: click the red square on the Run window menu panel.
  * COMMAND LiNE: Run the application from command line by opening a terminal window, changing directories to the folder of the application and typing "mvn spring-boot:run"
    * To verify the DemoApplication has successfully launched, open another terminal window and type: "curl http://localhost:8080/actuator/health"
      * Since you have added actuator, you sould receive a body: `{"status":"UP"}`
      * If the DemoApplication is not loaded, the curl command will return something similar to: _curl: (7) Failed to connect to localhost port 8080: Connection refused_ 
    * To stop the application press CTRL-C in the terminal window running the DemoApplication.
* Congrats! You have successfully created and run your first spring-boot application using tools from the Spring-Boot web site. :)

## 1. Hello World!
* Now that you have shown you can launch and stop a Spring Boot app, let's move on to our micro-badge project.
* Following the instructions in the [README.md](README.md), you should have forked this repository
  * If you have not done so, do it now so you have a local copy of the spring-boot-learning-guide project.
* Use IntelliJ to open your copy of the spring-boot-learning-guide pom.xml and import the spring-boot-learning-guide project. 
* Using IntelliJ, find the HelloWorldControllerTest, and run it... it should fail (expecting a 200, but actually receiving a 404).
* Typically rest controllers have the following annotations: @RestController and @RequestMapping. @RestController tells springboot 
    where to find your endpoints, and the @RequestMapping maps to the endpoint name. From the [Spring Boot guide](https://spring.io/guides/gs/spring-boot/) we read:
        _The class is flagged as a @RestController, meaning it's ready for use by Spring MVC to handle web requests. @RequestMapping maps / to the index() method. When invoked from a browser or using curl on the command line, the method returns pure text. That's because @RestController combines @Controller and @ResponseBody, two annotations that results in web requests returning data rather than a view_
* Find the HelloWorldController code and insert the @RestController annotation above the @RequestMapping (already included) and run the HelloWorldControllerTest again.
* Notice the new failure. Modify the HelloWorldController to make the test pass.

## 2. Application Boot
* This demo application requires a PostgreSQL database.  We will use Docker to launch a PostgreSQL database.
* If you do not have Docker installed, please install it now.  You can find instructions here: [https://docs.docker.com/install/](https://docs.docker.com/install/)
* Once Docker is installed, you can launch a PostgreSQL database by running the following command:
  >     docker run --name my-postgres-container -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres:latest
* This will launch a PostgreSQL database with the following details:
  * username: postgres
  * password: mysecretpassword
  * port: 5432
  * database: postgres
  * host: localhost
* These same details must match the application.yml file in the spring-boot-learning-guide project.
* Start the application locally by running the Application class.  You can do this from IntelliJ or from the command line using the following command:
  >     mvn spring-boot:run
* NOTE: Please make sure you have stopped the application before continuing to the next step.
    
## 3. Is It Healthy?
* Run _HealthTest_ - it should fail, not finding the /actuator/health endpoint.
* Notice the following dependency in the pom.xml:
  >     <dependency>
  >        <groupId>org.springframework.boot</groupId>
  >        <artifactId>spring-boot-starter-actuator</artifactId>
  >      </dependency>    
* The "health" endpoint is automatically enabled, but is currently disabled in the application.yml file. Modify the health endpoint configuration in the application.yml and enable the health endpoint by moving it from the "exclude" list to the "include" list. Your management sections should now look like this:
  >     management:
  >       endpoints:
  >         web:
  >           exposure:
  >             include: health
  >             exclude: info,metrics,prometheus
  >           base-path: /actuator

* Run _HealthTest_ again - One of the tests should be passing.
* Modify the application.yml file again and enable the db and diskspace health details. Add the following yml under the "management" configuration:
* You'll also need to add the "show-details" options set to "always". Your management section should now look like this:
  >     management:
  >       health:
  >         db:
  >           enabled: true
  >         diskspace:
  >           enabled: true

  >       endpoint:
  >         health:
  >           enabled: true
  >           show-details: always

* Run _HealthTest_ again - Two of the tests should be passing.
* Modify the application.yml file again and enable the "readiness" and "liveness" proves. This is done by adding the following under the "management" configuration:
  >     management:
  >       endpoint:
  >         health:
  >           probes:
  >             enabled: true

* Your health endpoint section should now look like this:
  >       endpoint:
  >         health:
  >           enabled: true
  >           show-details: always
  >           probes:
  >             enabled: true

* Run _HealthTest_ again - All tests should be passing.
* You can find more details about these actuator endpoints and healthcheck on the [spring-boot documentation website](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.enabling)

## 4. Lets Talk!
* In this section we leverage RestTemplate to add capabilities to our simple app.
* Please review [The Guide to RestTemplate](http://www.baeldung.com/rest-template) (http://www.baeldung.com/rest-template) to better understand it's capabilities.
* Run _OpenLibraryClientTest_ - it should fail, because the client is not yet calling the OpenLibrary API.
* In the _OpenLibraryClient_ class, add a _RestTemplate_ field but do NOT add it to the constructor params.  For various reasons, you should not add the restTemplate 
    directly to the constructor. Instead, add a _RestTemplateBuilder_ to the constructor parameters, and construct a restTemplate within the constructor:
    >    restTemplate = restTemplateBuilder.rootUri(olRootUri).build();
* NOTE: the restTemplate is using the passed in "olRootUri", which comes from the "openlibrary.root.url" application property - configured in the application.yml file. 
    By constructing the restTemplate with a rootUri you will not need to worry about setting the host uri when making a get / post / delete request.
* In the "findAuthor" method, replace empty return with the following:
    >     String searchUrl = UriComponentsBuilder.fromPath("/search/authors.json")
    >             .queryParam("q", authorName)
    >             .queryParam("limit", 1)
    >             .build()
    >             .toString();
    >       return restTemplate.getForObject(searchUrl, OpenLibraryResource.class);
    >     }
* This is using the RestTemplate to make a GET request to the OpenLibrary API. The UriComponentsBuilder is a handy way to build up the url, including query parameters.
* Re-run _OpenLibraryClientTest_ and verify the test passes

## 5. Enabling Endpoints via Annotations!
* Run the tests in _BooksControllerTest_ class - they should all fail
* Add in the necessary @RestController and @RequestMapping annotations so that you create a "books" endpoint in _BooksController_
* Add the following annotation directly above the class definition for _BooksController_:
>     @RestController
>     @RequestMapping("/books")

* Add the following annotation directly above the _createBook_ method in _BooksController_:
>     @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
>     @ResponseStatus(code = HttpStatus.CREATED)
* The @PostMapping provides detailed instructions to Spring allowing for sub-paths to the original path declared by the _RequestMapping_ annotation.
  This annotation also indicates what type of media details this particular endpoint will return.
* The @ResponseStatus annotation tells Spring what HTTP status code to return when this endpoint is called.
* 
* Add the following annotation directly above the _getBook_ method in _BooksController_:
>     @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
>     @ResponseStatus(code = HttpStatus.OK)
* The @GetMapping provides detailed instructions to Spring allowing for sub-paths to the original path declared by the _RequestMapping_ annotation.
  This annotation also indicates what type of media details this particular endpoint will return. The {bookId} will be used as a path parameter in the getBook method.
* Again The @ResponseStatus annotation tells Spring what HTTP status code to return when this endpoint is called.

* The rest of the endpoints already have the correct annotations, so you can leave them as is.
* Re-run the tests in _BooksControllerTest_ class - they should all pass

## 6. Exceptional Advice!
* Run the tests in _AuthorsControllerTest_ class - they should all fail
* Add in the necessary @RestController and @RequestMapping annotations so that you create a "authors" endpoint in _AuthorsController_
* Add the following annotation directly above the class definition for _AuthorsController_:
>     @RestController
>     @RequestMapping("/authors")
* The rest of the endpoints already have the correct annotations, so you can leave them as is.
* Re-run the tests in _AuthorsControllerTest_ class - the basic ones should all pass
* Add the following to the verifyAuthor method in _AuthorService_:
>     OpenLibraryResource olAuthor = openLibraryClient.findAuthor(author.getAuthorName());
>     if (olAuthor.getNumFound() == 0) {
>       throw new OlAuthorNotFoundException(author.getAuthorName());
>     }
* This is making use of the OpenLibraryClient to verify that the author exists in the OpenLibrary API.
* Re-run the tests in _AuthorsControllerTest_ class - it is still failing, but now with a new error:
>     org.opentest4j.AssertionFailedError:
>     Expected :400 BAD_REQUEST
>     Actual   :500 INTERNAL_SERVER_ERROR
* We need to add an exception handler to handle the _OlAuthorNotFoundException_.
* find the _handleOlAuthorNotFoundException_ method in _ExceptionAdvice_ and add the following annotation above that method:
>     @ExceptionHandler(OlAuthorNotFoundException.class)
>     @ResponseStatus(HttpStatus.BAD_REQUEST)
* This tells Spring that this method should be called when an _OlAuthorNotFoundException_ is thrown, and that the HTTP status code should be 400.
* Re-run the tests in _AuthorsControllerTest_ class - they should all pass

## 7. Who Am I?
* Springboot actuators also have a built in info section that can easily be extended.
* Enable the info endpoint by moving "info" from the exclude section to the include section in the application.yml. It should now look like this:
>       include: health,info
>       exclude: metrics,prometheus
* Start the app, and load [http://localhost:8080/actuator/info](http://localhost:9000/info) and notice that it isn't very useful yet.
* Add the following to application.yml under the "management" section. This will enable the java and environment details being provided by the info endpoint
>     info:
>       java:
>         enabled: true
>       env:
>         enabled: true

* Now add the following to the application.yml. This is at the root level. This will enable the app name, description, and version details being provided by the info endpoint
  >     info:
  >       app:
  >         name: @project.artifactId@
  >         description: @project.description@
  >         version: @project.version@
* Re-start the app, and re-load the info url ([http://localhost:8080/actuator/info](http://localhost:8080/actuator/info))
* Notice that the info url now has something useful, taken straight from the project's pom.xml.
* There are other ways to add info to the info endpoint, but this is the easiest way to get started. You can find out more here: https://reflectoring.io/spring-boot-info-endpoint/
* Run _InfoTest_ - tests should all be passing.

## 8. Let's Count!
* Spring actuators have built in metrics. These can be extended, but let's just take a look at what comes out of the box
* Enable the metrics endpoint by moving "metrics,prometheus" values from the exclude section to the include section in the application.yml. It should now look like this:
>       include: health,info,metrics,prometheus
>       exclude: 
* Start the app, and use a browser to load the following URL:  [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
* Notice that there is no metrics yet for the "http.server.requests".
* Load [http://localhost:8080/hello](http://localhost:8080/hello), and then go back and refresh the metrics url. You should see some new "http.server.requests" metrics
* Load [http://localhost:8080/actuator/metrics/http.server.requests](http://localhost:8080/actuator/metrics/http.server.requests) and notice the details of the metrics
* Run _MetricsTest_ - The metric specific tests are now passing
* The prometheus endpoint is already enabled, but we aren't yet exporting the data. 
* Enable exporting metrics to prometheus by adding the following to the application.yml under the "management" section
  >     prometheus:
  >       metrics:
  >         export:
  >           enabled: true
* Prometheus provides a simple way to query the metrics. Start the application and go to: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus) 

## 9. Was it useful?
* Thank you for taking the time to complete this learning guide!  I hope you found it useful.
* Please take a moment to provide feedback or add to the discussion here: [https://github.com/jd-code-ninja/spring-boot-learning-guide/discussions/7](https://github.com/jd-code-ninja/spring-boot-learning-guide/discussions/7)