[![Build Status](https://travis-ci.org/lukaszrys/money-tracker.svg?branch=develop)](https://travis-ci.org/lukaszrys/savemaker)

# money-tracker 

## Account Service

### Endpoints
* POST **/api/accounts** - create an account
* POST **/api/accounts/{accountId}/balance/add** - add amount to the account balance
* POST **/api/accounts/{accountId}/balance/subtract** - subtract amount from the account balance

## Account History Service