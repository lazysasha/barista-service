# Build your own coffeehouse

Congratulations! You are the proud owner of a new franchise license of CoffeeBucks ™, so you're allowed to open your own coffeehouse! The coffeehouse has 4 major components:

* The order counter, where you can ask for a menu and place an order
* The administrator (order persist), who will enter you order in the order database
* The barista, who will pick up the order and make the coffee
* The delivery counter, where you can pick up your coffee.

Furthermore, the CoffeeBucks ™ headquarters tries to make us much money from you as possible, so it's not allowed for you to determine your own prices. So for each and every order, you have to get the latest price from headquarters (you'll see that they're rather erratic!).



![coffeehouse](C:\data\barista-service\coffeehouse.jpg)

### Build REST controller

- Write GET endpoint for menu echoing hello -> use config property
- Write REST client for prices connecting to headquarters (service on AWS)
- Merge price info with menu

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

### Fault tolerance

* There is a new price endpoint. However, it's in alpha state and flaky. We'll make up our own price in that case. Build a retry and fallback method to counter this.

### 

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