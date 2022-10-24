# TestAssignment3 - Booking System
Made by Mathias Parking
## Requirement implementation
1. *It must be possible to create customers, employees and bookings.*
  - Each service can be found in the *service* package, and each repository can be found in the *datalayer* package.
  - DTO's and other classes used, are found in the *dto* package.
2. *A customer may have a phone number (this change requires a database migration script).*
  - All migration scripts can be found at *src/main/resources/db/migration*, including the table alteration script for the phone number.
3. *When booking an appointment with a customer, an SMS must be sent.*
  - While the implementation of this wasn't up to us, the tests for this interface are found in the *src/test/java/unit/servicelayer/sms* folder.

## Unit- and implementation tests
### Unit tests
- Unit tests are found in the *src/test/java/unit/servicelayer* folder. These contain tests using Junit and Mockito.
### Integration tests
- Integration tests for the datalayer and service layer respectively, are found in the *src/test/java/integration* folder. 
These tests are made possible with the use of Flyway and Testcontainers. All 4 sql migration scripts are run before these tests, to ensure a consistent database testing environment.
