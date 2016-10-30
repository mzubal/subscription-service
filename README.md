# Subscription Service
This is a sample project implementing simple micro-service using Spring-boot, Undertow and Apache Camel.
## Installing and running the service
Clone or download the repository and go the folder.

The service can be built and started running :
```cmd
mvnw spring-boot:run
```
or on Windows
```cmd
mvnw.cmd spring-boot:run
```

You can verify the successful start by entering following URL into your browser:
http://localhost:8090/subscription-service/api-doc

That shall view the swagger json describing the service.
## Using the service
You can test the service functionality using Swagger-UI:
  - go to http://petstore.swagger.io/
  - on the page, replace http://petstore.swagger.io/v2/swagger.json with http://localhost:8090/subscription-service/api-doc
  - hit *Explore* button
  - click on the *subscription-service*
  - observe the API
  - start with creating the Subscription using *PUT* operation on subscription
  - copy/paste the returned guid to use for *GET* or *POST* operations on subscription

## Known limitations
  - there might be some additional comments, but the code should be easily readable
  - missing builders for domain objects
  - there is no logging at this moment except for Camel traces (which are quite enough anyway)
  - there is no persistence - the service is keeping all data in memory, but that can be easily changed by providing different implementation of *SubscriptionService*
  - the exception handling is very simple (but works fine for all common situations)
  - the unit tests are very simple, but they cover basic happy-day scenarios