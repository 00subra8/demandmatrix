Tech Used:
	Spock with Groovy for Unit tests
	Springboot as the rapid application dev framework with inbuilt container
	Apache utils for string and collection manipulation
	Maven as build tool
	Json as input medium
	Techniques like defensive copying used to maintain integrity of current state.
	Abstraction between Controller, DAO and service layer practised.

Assumptions:
•	Database can be respawned during each instance restart
•	Quantity to be between 1 and 25
•	Price quoted to be between 500 and 2000
•	BUY and SELL are the only trade types [Enumerated]
•	New entry triggers BUY or SELL Operation in all existing entries accordingly.
•	Current state of all entries to be displayed after each new trade as it could have a ripple effect.
•	Values to be taken dynamically from input no dormant entries present.

Sample json to input:
{
  "tradeType": "BUY",
  "price": "1200",
  "quantity": "20",
  "orderTimeStamp": "10:10"
}

Please find below, on how to navigate through each endpoint feature:

1.	/doTrade
Application tested with postman app:
a.	First Successful buy trade:
 
b.	Successful Second addition:
 

c.	Successful third addition [No Buy/Sell trade happens as expected]
 
d.	Final state using /getState – After adding everything in problem data table

[
    {
        "tradeId": 1,
        "trade": {
            "tradeType": "BUY",
            "price": 1000,
            "quantity": 8,
            "orderTimeStamp": "10:00"
        }
    },
    {
        "tradeId": 2,
        "trade": {
            "tradeType": "SELL",
            "price": 900,
            "quantity": 0,
            "orderTimeStamp": "10:05"
        }
    },
    {
        "tradeId": 3,
        "trade": {
            "tradeType": "SELL",
            "price": 1100,
            "quantity": 0,
            "orderTimeStamp": "10:06"
        }
    },
    {
        "tradeId": 4,
        "trade": {
            "tradeType": "SELL",
            "price": 1100,
            "quantity": 0,
            "orderTimeStamp": "10:08"
        }
    },
    {
        "tradeId": 5,
        "trade": {
            "tradeType": "BUY",
            "price": 1200,
            "quantity": 7,
            "orderTimeStamp": "10:10"
        }
    }
]

e.	Trade Type invalid:

 

f.	Price invalid:

 


g.	Invalid Quantity

 
Spock Unit test :
 
