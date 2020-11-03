# Build your own barista

Summary of assignments:



### Build REST controller

- Write GET endpoint for menu echoing hello -> use config property
- Write REST client for prices connecting to headquarters (service on AWS)
- Merge price info with menu
- Connect to unreliable endpoint and solve with *circuitbreaker* and *retry*

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

### Bonus round

- Make DB access of Barista fully reactive with R2DBC (unprepared)
- Build native app with GraalVM (unprepared)