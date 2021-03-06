# Spring Framework & Hibernate Practice Roadmap
Copyright (C) Viorel Contu 2020.

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
    * [ Authentication ](#authentication)
    * [ Authorization ](#authorization)
    * [ REST Endpoints ](#endpoints)
    * [ Swagger-UI ](#swagger)
    * [ Alternative Http Clients ](#httpclients)
5. [ Configuration ](#configuration)
6. [ Future Plans ](#future)
7. [ Recommended Links ](#recommendation)


<a name="desc"><a>
## 1. Description
This application serves as a road map to learning many technologies necessary to build and run a web service. It is build on spring boot. The app provides an example of using the following instruments:
- **Java 11**
- Spring Boot 
- Spring Web; Jackson, MapStruct; RestTemplate; Swagger 
- Spring Data; Hibernate; H2; Oracle
- JUnit Jupiter; AssertJ; Mockito; RestAssured
- Lombok
- Logback

The application connects to [CoinMarketCap](https://coinmarketcap.com/) Api to retrieve cryptocurrency price quotes and share this information through a selection of REST endpoints. Please, see how to configure application to access CoinMarketCap [here](#coin).


<a name="setup"><a>
## 2. General Requirements
Project requires:
- Java 11
- Apache Maven
- Docker (optional)

Docker will allow to run containers for both application and Oracle database.  


<a name="install"><a>
### 2.1 Quick Setup
1. Configure maven properties in pom.xml
```xml
<properties>    
        <oracle.cdb>cdb</oracle.cdb>
        <oracle.pdb>pdb</oracle.pdb>
        <oracle.sys.password>orclpass</oracle.sys.password>
        <oracle.host>oracle-rd</oracle.host>
        <oracle.roadmap.username>roadmap</oracle.roadmap.username>
        <oracle.roadmap.password>roadpass</oracle.roadmap.password>
        <oracle.url>jdbc:oracle:thin:@//${oracle.host}:1521/${oracle.pdb}</oracle.url>
        <coinmarket.host>https://sandbox-api.coinmarketcap.com</coinmarket.host>
        <!-- you must supply this argument during maven builds-->
        <coinmarket.token/>
</properties>
```
These properties will be used for application property files and oracle credentials. You can live the defaults. 
2. Obtain a free API key from Coin Market Cap from https://sandbox.coinmarketcap.com/
Write it in 
```xml
<coinmarket.token>YOUR-API-HERE</coinmarket.token>>
```
3. Build your application and Docker files 
While in project root directory: `mvn clean package -P docker` 
4. Start both roadmap and oracle inside container
While in project root directory: `docker-compose up -d`
Wait for oracle database to start and load.  
5. Open src/test/http and try any http request inside

### 2.2  Oracle DB Flyway Migration
The application will migrate the src/main/resources/db/migration sql script files **automatically** upon starting.
The Flyway is also accessible as a maven plugin. To manually trigger flyway migration: `mvn flyway:migrate`

<a name="test"><a>
### 2.2 Test
`mvn clean test` to run unit tests

`mvn clean verify` to run integration tests

<a name="coin"><a>
### 2.3 Coin Market Cap
First of all, go to [CoinMarketCap Sandbox](https://sandbox.coinmarketcap.com) create free sandbox account and obtain an API key. The key should be inserted into config file:

* ***src/main/resources/roadmap.properties***
```properties
crypto.portal.token=<Insert API Key here>
crypto.portal.host=https://sandbox-api.coinmarketcap.com
crypto.listing.limit=20
```
The application connects to Coin Market Sandbox. To connect to Coin Market production servers, you need a different production account. Change the property `crypto.portal.host` to point to the URL of the production server.   
 With a valid account on Coin Market either Sandbox or production, and valid API key for that account you can now use the **/quotes** endpoint and make HTTP REST calls to obtain market values for different crypto-currencies. 

<a name="run"><a>

## 3. Running
To start the application, while inside the code repository, you can use maven:
`mvn spring-boot:run` 

To execute the deployable jar: 
`java -jar roadmap-2.0.jar`

<a name="spring"><a>
### 3.1 Spring Profiles
The **Spring profiles** define how the application actually runs.    

* ***no-security*** profile: Disables authentication and authorization for most of endpoints.

#### 3.1.1 Default spring profile
The application now includes all sort of properties configured for debugging and learning:
* hibernate SQL queries
* transaction management messages  
* swagger ui endpoint


#### 3.1.3 Configuring oracle database
You need to create a user in your Oracle DB in order to be able to migrate the data. 
Connect to sys user and issue the following commands:

* `CREATE USER ROADMAP IDENTIFIED BY PASSWORD`
* `GRANT CREATE SESSION, CREATE TABLE, CREATE VIEW, CREATE SEQUENCE TO ROADMAP`
* `GRANT UNLIMITED TABLESPACE TO ROADMAP`

Consult Oracle reference manual for extra configuration.

#### 3.1.4 Switching Spring profiles
* InteliJ IDEA: 
    1. **CTRL+SHIFT+A**
    2. Type `edit configurations`<ENTER>
    3. Select in right column **Spring Boot** -> **RoadmapApplication** 
    4. **Configuration Tab** -> Section **Spring Boot**
    5. Edit **Active Profiles** field with **prod** profile
    6. Run Roadmap Application from InteliJ IDEA
* From command line: `java -jar roadmap-2.0.jar -Dspring.profiles.active=dev`
* From command line with maven: `mvn spring-boot:run -Drun.arguments="--spring.profiles.active=dev"`


<a name="usage"><a>
## 4. Application Usage
<a name="endpoints"><a>
Application will only serve authenticated and authorized users. The required data is pulled from database. 

<a name="authentication"><a>
### 4.1 Authentication (Error: HttpStatus 401 Unauthorized)
To make calls you need, to include in request Header your api key. Consult the migration scripts located in 

* `src/main/resources/db/migration` for the user tokens to identify yourself as that specific user. 

example: `X-API-KEY: 12345678-1234-1234-1234-111111122211`

<a name="authorization"><a>
### 4.2 Authorization (Error: HttpStatus 403 Unauthenticated)
Different api calls require different user roles. Please consult the migration scripts to select a user authorized to make a specific call. 

<a name="endpoints"><a>
### 4.3 REST Endpoints
* `/users`  performs different CRUD operations on users
* `/map`    maps the ids and symbols for crypto-currencies and normal currencies used by app    
* `/quotes` retrieves market quotes for selected crypto-currencies

<a name="swagger"><a>
### 4.3 Swagger-UI
You can inspect REST endpoints with Swagger UI: [http://localhost:8080/api/swagger/](http://localhost:8080/api/swagger/)

Following api specifications are available (select through combobox upper right):
* open-api - custom specification written manually (more detailed, with examples)
* default - automatically generated by swagger based on class definitions

The custom api file location is defined in application.yml:
```properties
swagger.custom-api: /api/roadmap-api.yml
```

<a name="httpclients"><a>
### 4.4 Alternative HTTP Clients
* ***src/test/resources/http/*** 

contains http template files:
- users.http
- map.http
- quotes.http

You can invoke the HTTP requests in these files directly from InteliJ IDEA.


<a name="configuration"><a>
## 5. Configuration 
No other configuration is necessary. 

For testing purposes, a different sql file is loaded, located in
 * ***src/test/resources/db/test-data.sql***


<a name="future"><a>
## 6. Future Plans
- [X] Add exception handler for RestTemplate
- [X] Generate custom API schema for Swagger
- [X] Upgrade to a Oracle DB instead of H2 and wire Flyway
- [X] Adjust API endpoints to require authentication tokens active for users
- [X] Implement authorization for different user roles (with Spring AOP)
- [X] Add caching mechanism for CoinMarketCap requests  
- [X] Migrate to Java 11
- [X] Containerize application with Docker 
- [ ] Add audit feature integrated with RabbitMQ
- [ ] Add DB Unit for integration tests
- [ ] Add Zipkin for log tracing
- [ ] Add credits for users which will be used when they consume API
- [ ] Add wiremock to enable integration tests for **/quotes** endpoints
- [ ] Complete test coverage
- [ ] Add Swagger Validator for endpoints that require message body
- [ ] Add a sandbox profile that substitutes CoinMarketCap functionality with WireMock
- [ ] Add a connection retry mechanism
- [ ] Migrate to Spring Security
- [ ] Migrate to PostgreSQL


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

#### Swagger, Open API, Documenting Endpoints
* https://editor.swagger.io/   
* https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
* https://swagger.io/specification/
* https://idratherbewriting.com/learnapidoc/docapis_introtoapis.html
* http://json-schema.org/understanding-json-schema/index.html

#### RestAssured 
* https://github.com/rest-assured/rest-assured/wiki/Usage
* https://www.testingexcellence.com/parse-json-response-rest-assured/

#### Junit
* https://www.baeldung.com/parameterized-tests-junit-5

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