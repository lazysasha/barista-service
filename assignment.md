# Build your own coffeehouse

Congratulations! You are the proud owner of a new franchise license of CoffeeBucks ™, so you're allowed to open your own 



### Build REST controller

- Write GET endpoint for menu echoing hello -> use config property
- Write REST client for prices connecting to headquarters (service on AWS)
- Merge price info with menu

### Fault tolerance

* There is a new price endpoint. However, it's in alpha state and flaky. We'll make up our own price in that case. Build a retry and fallback method to counter this.

### Ordering a drink

- Write POST endpoint, citing name and beverage
- Write Panache entity OrderEntity (NOT Order since it's a keyword)
- Store name, beverage and orderId in DB (H2 mem)

### Tell the barista to make the coffee

- Send orderId to Barista through Kafka channel with topic "orders"
- Barista should get the order from the DB and make the beverage -> it will not working since it's a blocking DB operation
- Fix with @Blocking

### Send coffee to the counter

- Make a reactive endpoint that listens to the "completedOrders" topic
- Barista should send (name, coffee) to outgoing channel with completed orders topic
- Connect browser to reactive endpoint

### Test the whole system

- Send order to order endpoint
- completed order should come out the other end

### Observability

- Look at live and ready endpoints
- Build a readyness probe for the database



### Bonus round

- Make DB access of Barista fully reactive with R2DBC (unprepared)
- Circuitbreaker
- Build native app with GraalVM (unprepared)