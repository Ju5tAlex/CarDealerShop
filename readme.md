# CarDealerShop
Educational project that emulates possible interactions between entities in real car dealership. 

## General info
Focus of this application is to have basic CRUD features for entities that exists in a database, and some interaction with message queue. Interaction done through http endpoints(REST). Application needs an external database and message queue server.

## Technologies
* Spring Boot
* Spring Data
* Hibernate
* Jackson
* Swagger
* DataBase: MySQL
* Message Queue: RabbitMQ
* Server: TomCat(built-in Spring Boot) or WebSphere Liberty

## Setup
Application uses default ports and setting for MySQL database(only username and password change required). No additional settings for RabbitMQ required.

## Starting application
Use this command to start application on Liberty server:
`mvn clean package liberty:run`

## Interaction with application
Application has Swagger UI built-in. You can use it to test all endpoints available. Use browser and this links:
* [TomCat server](http://localhost:8080/swagger-ui/)
* [WebSphere Liberty server](http://localhost:9080/swagger-ui/)

## Basic functional
There is 3 main endpoint available:
`/cars`
`/parts`
`/clients`

Every endpoint represents certain entity in database and has basic CRUD features available for it. `/parts` has a few endpoints to interact with Message Queue.

## Entity structure
Database has entity structure with Many-to-One and Many-to-Many relationships. There is two unmodifiable entity: Manager and Manufacturer. They have clients and parts respectively. And the main entity is car. It has parts and clients.
