# Money Transfer

How to start the moneytransfer application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/moneytransfer-1.0.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications com.moneytransfer.health enter url `http://localhost:8081/healthcheck`

AccountResource - /accounts
---

**POST - /create** - creates a new account with initial balance

Sample curl request:
```
curl -H "Content-Type: application/json" -X POST -d '{"account_number":"1", "balance":100}' http://localhost:8080/accounts/create
```

**GET - /{id}** - Fetches account information

Sample curl request:
```
curl -H "Content-Type: application/json" -X GET  http://localhost:8080/accounts/1
```

TransactionResource - /transactions
---

**POST - /transfer** - initiates a transfer transaction between two accounts

Sample curl request:
```
curl -H "Content-Type: application/json" -X POST -d '{"debited_account":"1", "credited_account":"2", "transfer_amount":10}' http://localhost:8080/transactions/transfer
```
