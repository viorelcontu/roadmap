# Practice Roadmap
Copyright (C) Viorel Contu 2019.

This application source code can only be used for educational purposes. 

## Table of Contents

1. [ Description ](#desc)
2. [ General Setup ](#setup)
    * [ Install ](#install)
    * [ Test ](#test)
    * [ Coin Market Cap ](#coin)
3. [ Running ](#run)
    * [ Spring Profiles ](#spring)
4. [ Usage ](#usage)
    * [ REST Endpoints ](#endpoints)
    * [ Swagger-UI ](swagger)
    * [ Alternative Http Clients ](#httpclients)
5. [ Configuration ](#configuration)
6. [ Future Plans ](#future)
7. [ Recommended Links ](#recommendation)


<a name="desc"><a>
## 1. Description
This application serves as a road map to learning many technologies necessary to build and run a web service. It is build on spring boot. The app provides an example of using the following instruments:
- Spring Boot 
- Spring Web; Jackson, MapStruct; RestTemplate; Swagger 
- Spring Data; Hibernate; H2
- JUnit Jupiter; AssertJ; Mockito; RestAssured
- Lombok
- Logback

The application connects to [CoinMarketCap](https://coinmarketcap.com/) Api to retrieve cryptocurrency price quotes and share this information through a selection of REST endpoints. Please, see how to configure application to access CoinMarketCap [here](#coin).


<a name="setup"><a>
## 2. General Setup
Project requires Apache Maven to be installed on machine.

<a name="install"><a>
### 2.1 Install
From command line, while inside the project directory root:

`mvn clean install` will install the artifact into maven local repository

<a name="test"><a>
### 2.2 Test
`mvn clean test` to run unit tests

`mvn clean verify -Prun-its` to run integration tests

<a name="coin"><a>
### 2.3 Coin Market Cap
Create a free active account on [CoinMarketCap Sandbox](https://sandbox.coinmarketcap.com) and obtain an API key. Please edit 
* ***src/main/resources/application-dev.properties*** 

and add your API key there. 
```properties
crypto.portal.token=<Insert API Key here>
```
This will unlock the **/quotes** endpoint for **dev** profile. 

In a similar manner, with an active account on [CoinMarketCap Pro API](https://coinmarketcap.com/api/)  you can edit 
* ***src/main/resources/application-prod.properties*** 

with your API key to unlock the usage of all the endpoints.

To switch between using coinmarketcap **sandbox** or **pro-api**, see the [spring profiles](#spring) section. 


<a name="run"><a>
## 3. Running
To start the application from command line on a machine with maven installed: 

`mvn spring-boot:run`

As an alternative, you can run start the jar file directly, but you will need to add manually all the dependencies to the class path

`java -cp <classpath dependencies>  -jar roadmap-1.0.jar `

<a name="spring"><a>
### 3.1 Spring Profiles

The application has the following profiles defined: 
1. **dev** - connects to *sandbox-api.coinmarketcap.com*
2. **prod** - connects to *pro-api.coinmarketcap.com*

By default, the **dev** profile is activated through configuration set in 
* ***src/main/resources/application.properties***

```properties
spring.profiles.active=dev
```

For practice, you don't need the **prod** profile, but if you want to activate it you can either do it by:  
* Editing the *application.properties* config
* InteliJ IDEA: 
    1. **CTRL+SHIFT+A**
    2. Type `edit configurations`<ENTER>
    3. Select in right column **Spring Boot** -> **RoadmapApplication** 
    4. **Configuration Tab** -> Section **Spring Boot**
    5. Edit **Active Profiles** field with **prod** profile
    6. Run Roadmap Application from InteliJ IDEA
* From command line: `java -jar roadmap-1.0.jar -Dspring.profiles.active=prod -cp <class-path dependencies>`
* From command line with maven: `mvn spring-boot:run -Drun.arguments="--spring.profiles.active=prod"`


<a name="usage"><a>
## 4. Usage
<a name="endpoints"><a>
### 4.1 REST Endpoints
* `/users`  performs different CRUD operations on users
* `/map`    maps the ids and symbols for crypto-currencies and normal currencies used by app    
* `/quotes` retrieves market quotes for selected crypto-currencies

<a name="swagger"><a>
### 4.2 Swagger-UI
You can inspect REST endpoints with Swagger UI: [http://localhost:8080/swagger-ui.html/](http://localhost:8080/swagger-ui.html/)

Currently, the JSON schema is generated automatically by swagger. There is no extra documentation included.


<a name="httpclients"><a>
### 4.3 Alternative HTTP Clients
* ***src/test/resources/http/*** 

contains http template files:
- users.http
- map.http
- quotes.http

You can invoke the HTTP requests in these files directly from InteliJ IDEA.


<a name="configuration"><a>
## 5. Configuration 
No other configuration is necessary. The application uses an in-mem H2 database for all profiles and tests. All the H2 configuration properties are included in
 * ***src/main/resources/application.properties***
 
```properties
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.data=classpath:db/data.sql
spring.h2.console.enabled=true
```

For testing purposes, a different sql file is loaded, located in
 * ***src/test/resources/db/test-data.sql***


<a name="future"><a>
## 6. Future Plans
- [X] Add exception handler for RestTemplate
- [ ] Add wiremock to enable integration tests for **/quotes** endpoints
- [ ] Adjust API endpoints to require authentication tokens active for users
- [ ] Generate custom API schema for Swagger
- [ ] Add Swagger Validator for endpoints that require message body
- [ ] Add credits for users which will be used when they consume API
- [ ] Add a sandbox profile that substitutes CoinMarketCap functionality with WireMock
- [ ] Add caching mechanism for CoinMarketCap requests.  
- [ ] Add a connection retry mechanism
- [ ] Upgrade to a normal SQL DB instead of H2 and wire Flyway


<a name="recommendation"><a>
## 7. Recommended Links
#### Project Lombok
* https://www.baeldung.com/intro-to-project-lombok

Also, you need to install the InteliJ plugin for lombok, which will make the IDE understand the annotations and allow you to use
getters and setters in code completion

#### Spring Framework Web MVC
* https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/web.html#spring-web

#### Writing your first Rest Controller
* https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration
* https://www.baeldung.com/spring-request-response-body

#### Error Handling in REST
* https://www.baeldung.com/exception-handling-for-rest-with-spring

#### Jackson 
* http://tutorials.jenkov.com/java-json/index.html
* https://www.baeldung.com/jackson-annotations
* https://www.baeldung.com/jackson-ignore-properties-on-serialization
* https://www.baeldung.com/jackson-ignore-null-fields
* https://www.baeldung.com/jackson-map

#### Entity to DTO conversion 
* https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application

#### MapStruct 
* https://www.baeldung.com/mapstruct
* http://mapstruct.org/documentation/stable/reference/html/

#### Swagger
* https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

#### RestAssured 
* https://github.com/rest-assured/rest-assured/wiki/Usage
* https://www.testingexcellence.com/parse-json-response-rest-assured/

#### AssertJ:
* https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html
* https://www.vogella.com/tutorials/AssertJ/article.html

#### Mockito
* https://www.vogella.com/tutorials/Mockito/article.html
* https://www.baeldung.com/mockito-series
* https://www.baeldung.com/mockito-argument-matchers
* https://www.baeldung.com/spring-mock-rest-template

#### CoinMarketCap API Documentation
* https://sandbox.coinmarketcap.com/api/v1/