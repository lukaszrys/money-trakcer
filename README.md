[![Build Status](https://travis-ci.org/lukaszrys/money-tracker.svg?branch=develop)](https://travis-ci.org/lukaszrys/savemaker)

# money-tracker 

## General stack

* Java 11
* Spring
* MongoDB (reactive)
* Webflux/Reactor/Webclient
* DDD / intro CQRS

## Account Service

### Additional stack
* JUnit 5

### Endpoints
* POST **/api/accounts** - create an account
* POST **/api/accounts/{accountId}/balance/add** - add amount to the account balance
* POST **/api/accounts/{accountId}/balance/subtract** - subtract amount from the account balance
* GET **/api/accounts/{accountId}** - return view for accounts for given accountId

## Account History Service

### Additional stack
* Groovy/Spock

### Endpoints
* POST **/api/expenses** - create an expense
* POST **/api/incomes** create an income
* GET **/api/transactions/{accountsId}** - return all transactions (incomes + expenses) attached to provided accountId