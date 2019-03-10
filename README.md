# mobile-subscriber

A REST Web-Service responsible of maintaining a database of mobile numbers, that are assigned to clients, along with some related information.

## Technology used

* Java 11
* Spring Boot 2.x
* Spring Data JPA
* Swaggwe 2.x

## Provided endpoints

- Return all mobile numbers from the database
  - `GET` - `http://localhost:8080/mobile-subscriber/api/subscribers`
- Return all mobile numbers that match the search criteria
  - by mobile number
    - `GET` - `http://localhost:8080/mobile-subscriber/api/subscribers/{msisdn}`
  - by onwer id
    - `GET` - `http://localhost:8080/mobile-subscriber/api/subscribers/owner/{customerIdOwner}`
  - by user id
    - `GET` - `http://localhost:8080/mobile-subscriber/api/subscribers/user/{customerIdUser}`
- Add a mobile number to the database
  - `POST` - `http://localhost:8080/mobile-subscriber/api/subscribers`
- Change a mobile number plan from prepaid to postpaid or vice versa
  - `PUT` - `http://localhost:8080/mobile-subscriber/api/subscribers/{msisdn}/plan`
- Delete a mobile number from the database
  - `DELETE` - `http://localhost:8080/mobile-subscriber/api/subscribers/{msisdn}`
- Assign different owners/users of a service
  - `PUT` - `http://localhost:8080/mobile-subscriber/api/subscribers/pre-paid`
  - `PUT` - `http://localhost:8080/mobile-subscriber/api/subscribers/post-paid`
