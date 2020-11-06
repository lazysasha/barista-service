# Build your own coffeehouse

Congratulations! You are the proud owner of a new franchise license of CoffeeBucks ™, so you're allowed to open your own coffeehouse! The coffeehouse has 4 major components:

* The order counter, where you can ask for a menu and place an order
* The administrator (order persist), who will enter you order in the order database
* The barista, who will pick up the order and make the coffee
* The delivery counter, where you can pick up your coffee.

Furthermore, the CoffeeBucks ™ headquarters tries to make as much money from you as possible, so it's not allowed for you to determine your own prices. So for each and every order, you have to get the latest price from headquarters (you'll see that they're rather erratic!).

![coffeehouse](coffeehouse.jpg)

### Prerequisites

- Java IDE 
- Java 11 JDK
- Docker
- Maven 3.6.2 (or higher)
- Git
- Either Postman, HttpClient (IntelliJ), curl or httpie (or whatever enables you to send POST messages)
- A database client (either IntelliJ Ultimate, Squirrel or something else)

### Getting started

- Clone the repository
- Start a Kafka server and database by executing
```
docker-compose up
```
- Start the Coffeehouse service by executing in another terminal
```
./mvnw quarkus:dev
```
Quarkus will start on port 8080 in development mode, which means that any change you make will result in an automatic redeployment. No need to restart the server (usually).


### Exercise 1: Getting the menu

Create a REST controller which returns the menu containing a greeting message and a list of products. You can use the existing `Menu` class as a data model. 

- Create a config property in the `appliction.properties` containing your greeting message.
- Inject the property as a field into the `OrderCounterResource` class
- Create a REST endpoint of type GET with path `/menu` that will return the menu containing the greeting message. 

> ![Test][check] Test the endpoint using a browser going to `http://localhost:8080/menu`

> You should now see the greeting message.
 
Retrieve the current prices for all beverages from Headquarters

- Create a declarative RestClient in the PricesClient interface that calls 'http://localhost:8080/prices/{productname}' where `productname` is the name of the beverage. You can use the `Price` class as a response model.
- Add the appropriate `mp-rest` (url, scope and connectTimeout) MicroProfile properties to the `application.properties`
- Inject the RestClient into the `OrderCounterResource` 
- For each beverage retrieve the current price and add the beverage and price as a `Product` to the menu.

> ![Test][check] Test the endpoint using a browser going to `http://localhost:8080/menu`

> You should now see the list of products.

### Exercise 2: Ordering a beverage

Create a REST controller which receives the order and sends it to the `OrderPersister`.

- Create a REST endpoint of type POST with path `/orders` that will receive an `Order`.
- Add the appropriate outgoing properties (connector, topic and serializer) to the `application.properties` (see the [Quarkus Kafka Guide](https://quarkus.io/guides/kafka#configuring-the-kafka-connector))
- Choose a topic name yourself, for instance `orders`
- Use the `io.quarkus.kafka.client.serialization.JsonbSerializer` class as the serializer class
- Inject an `Emitter` of type Order with a channel called `outgoing-orders`.
- Send the received order to the channel. 
- In the `OrderPersister` class create an `incoming` method that receives an `Order`.
- Connect the method to the channel `incoming-orders` using the `@Incoming`-annotation
- Log the incoming order.
- Add the appropriate incoming properties (connector, topic and deserializer) to the `application.properties` (see the [Quarkus Kafka Guide](https://quarkus.io/guides/kafka#configuring-the-kafka-connector))
- Use the `OrderDeserializer` as the deserializer class

> ![Test][check] Test the endpoint by executing a POST call to `http://localhost:8080/orders`
> ```json
> { 
>   "customerName": "Your name",
>   "beverage": "latte"
> }
> ```
> You should now see a log message in your console that the order has been received

#### Add Bean Validation

Send an order with a beverage that doesn't exist, for example an "Irish Coffee". You should now see an exception in the application because the beverage could not be found.
To prevent this we can add BeanValidation to the `orders` endpoint.

- Add a `@Valid`-annotation to the Order parameter of the endpoint

![Test][check] Test the endpoint by executing a POST call to `http://localhost:8080/orders`
> ```json
> { 
>   "customerName": "Your name",
>   "beverage": "Irish Coffee"
> }
> ```
> You should now get an HTTP Status code 400 - BAD REQUEST

### Exercise 3: Persist the order

Store the order in the database, so the Barista can pick it up later.

We are going to use the Active Record style using Panache. You can also use the Panache Repository (but that will not be explained here).

- The `OrderEntity` class must extend the `PanacheEntity` class and be a JPA entity.
- Add the fields from the `Order` to  the `OrderEntity`.
- Add the persisting logic to the `incoming`-method of the `OrderPersister` class. The `OrderEntity` class has now a lot of methods available, choose wisely.
- Make sure the method is transactional.
- Add the database properties to the `application.properties`
```properties
quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:tcp://localhost:1521/~/orders
quarkus.datasource.jdbc.driver=org.h2.Driver
quarkus.datasource.username=coffeehouse
quarkus.datasource.password=coffeehouse
quarkus.hibernate-orm.database.generation=drop-and-create
```

![Test][check] Post an order

You should now see an exception in the console log, complaining that the database operation is a blocking operation and is not allowed on an asynchronous thread.

- Add a `Blocking` annotation to prevent this (or you can make the entire database transaction reactive, but we don't recommend that right now)

![Test][check] Post an order and test if the order is persisted in the database by querying the OrderEntity table (using the url and credentials above)

### Exercise 4: Informing the Barista

Now we must inform the Barista that an order is ready to be processed. 

- Create a new incoming and outgoing channel pair in the `application.properties`.
- Choose a topic name (for example `prepare`).
- After the order has been persisted, the order has an id. Return this id as the result of the `incoming` method of the `OrderPersister` class.
- Annotate the `incoming` method with the `Outgoing` annotation using the outgoing channel name.

Now we are going to implement the Barista

- Create a config property containing the name of the barista and inject it into the `Barista` class.
- Create a void method `makeBeverage` that receives the order id.
- Log the order id

![Test][check] Post an order and see if the order id is logged to the console log.

### Exercise 5: Deliver the order to the delivery counter

Retrieve the order from the database, prepare the delivery and send it to the counter.

In the `Barista` class
- Create a new incoming and outgoing channel pair in the `application.properties`.
- Choose a topic name (for example `delivery`).
- Retrieve the order from the database using the order id
- Create a new `Delivery` instance, filling the barista name, customer name and the beverage
- Add the appropriate annotations
- Return the delivery as the result of the `makeBeverage` method

In the `DeliveryCounterResource` we will create a fully reactive endpoint that can be opened as a stream by a browser. 
The stream contains JSON content  
- Create a GET endpoint with path `/outcounter` and which produces `test/event-stream` media types
- Add the `SseElementType` annotation with the `application/json` media type 
- Inject a `org.reactivestreams.Publisher` of type `Delivery` which receives the messages from the `Incoming` channel. 
- The GET endpoint should return this publisher.

> ![Test][check] Open a new browser tab on `http://localhost:8080/outcounter` and post an order.
> You should see the delivery appear in the outcounter tab.

##### Congratulations! You have now an active CoffeeBucks™ franchise.




### Fault tolerance

* There is a new price endpoint. However, it's in alpha state and flaky. We'll make up our own price in that case. Build a retry and fallback method to counter this.

### 

### Test the whole system

- Send order to order endpoint
- Completed order should come out the other end

### Observability

- Look at live and ready endpoints
- Build a readiness probe for the database



### Bonus round

- Make DB access of Barista fully reactive with R2DBC (unprepared)
- Circuitbreaker
- Build native app with GraalVM (unprepared)

[check]: checkmark.png