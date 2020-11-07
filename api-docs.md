### Getting the menu

GET http://localhost:8080/menu

**Response model:**

Content-type: application/json

```json
{
    "greetingMessage": "Welcome to the Craftsmen Coffeehouse! Here's our menu! ",
    "menu": [
        {
            "beverage": "american",
            "price": 2.56
        },
        {
            "beverage": "espresso",
            "price": 8.99
        },
        {
            "beverage": "latte",
            "price": 3.01
        }
    ]
}
```

### Placing an order

POST http://localhost:8080/orders

**Request payload:**

Content-type: application/json

```json
{
	"customerName": "michel",
    "beverage": "espresso"
}
```



### Out counter

GET http://localhost:8080/outcounter

**Response model:**

Content-type: text/event-stream;element-type="application/json"

```
data: {"beverage":"espresso","createdBy":"John","customerName":"michel"}
data: {"beverage":"american","createdBy":"John","customerName":"ard"}
```

