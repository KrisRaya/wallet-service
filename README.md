## Wallet Service

A Service that provide and manages all of the wallet, including add new wallet, fetch all or one of registered wallet, and do top up wallet with some of amount.

## How to run service
All the service related is run by command of `docker-compose up` that provided by this service - [Wallet Service](https://github.com/KrisRaya/wallet-service).
Once the service is up, go to [Wallet Service Swagger UI page](http://localhost:8080/wallet/swagger-ui.html) to hit the provided API.

* When running `docker-compose` there's a chance build is failure when maven try to download dependency from maven central, please retry to run the command.  