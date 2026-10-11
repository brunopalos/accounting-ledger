# Demo Accounting Ledger

## Description

This is a demo implementation of a single-entry accounting ledger. It currently supports the followint operations:

- deposit to a checking account
- withdrawal from a checking account
- get account balance
- get account summary

It follows simplified versions of the Service Agreement and Posting Rules from Martin Fowler's accounting patterns. This implementation doesn't support Accounting Events and only used a simple static Service Agreement for the checking account, for demonstration purposes. However, it is possible to extend the implementation with more sophisticated Service Agreements and be extended with Accounting Events. It is also possible to extend it to become a double-entry or multi-leg ledger by changing the Posting Rules accordingly.

## How to build it

On a terminal, run:

    mvn clean install

## Testing

The project has unit and integration tests that are both run on the same phase by Surefire, so all can be run the following way:

    mvn test

## Running the service on local environment

On a terminal, run:

    mvn spring-boot:run

## Using the ledger

### Deposit

Depositing money on the account with ID 1:

    curl -X POST http://localhost:8080/api/v1/deposit-account/deposit/1 -H 'Content-Type: application/json' -d '{"value":"100"}'

    HTTP/1.1 200
    {
        "transactionId": 1,
        "errorMessage": null
    }

### Withdraw

Withdraw money from the account with ID 1:

    curl -X POST http://localhost:8080/api/v1/deposit-account/withdraw/1 -H 'Content-Type: application/json' -d '{"value":"95"}'

    HTTP/1.1 200
    {
        "transactionId": 2,
        "errorMessage": null
    }


### Account balance

Get balance from account with ID 1:

    curl -X GET http://localhost:8080/api/v1/deposit-account/balance/1

    HTTP/1.1 200
    {
        "value": 5
    }


### Account summary

Get account summary from account ID 1:

    curl -X GET http://localhost:8080/api/v1/deposit-account/summary/1

    HTTP/1.1 200
    {
        "balance": 5,
        "entries": [
            {
                "transactionId": 1,
                "amount": 100,
                "occurredAt": "2026-10-11T20:57:10.227938371Z"
            },
            {
                "transactionId": 2,
                "amount": -95,
                "occurredAt": "2026-10-11T20:57:34.754531484Z"
            }
        ]
    }

## Improvements

- Null safety and input validation.
- Split unit tests and integration tests.
- 