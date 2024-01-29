# Card Information Service:- Mintyn 

This project contains a Card Information Service. The primary goal is to provide our customers with the best possible experience by offering detailed information about their cards. This README will guide you through the key features and functionalities of our service. 

## Features

- User Authentication: Signup and Login
- Validity Status: Check whether the card is currently valid or not
- Card Scheme: Identify the card scheme, whether it's VISA, MASTERCARD, AMEX, or any other major card scheme.
- Bank Information: Discover the associated bank when available.
- 100% test coverage for all Controllers and Services.

## Software Design Patterns and Principles

This Project adheres to robust design patterns and principles, ensuring scalability, maintainability, and a seamless user experience. A few are:

- Dependency Injection.
- Single Responsibility Principle. 
- Open/Closed Principle.
- Reactive Programming.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL


### Installing

Clone the repository:

```bash
git clone https://github.com/ade-ife/Backend-Assessment-Mintyn
```

Navigate to the project folder 
```bash
cd Backend-Assessment-Mintyn
```

Navigate to application.properties in src/main/resources
```bash
Input your postgres database username, password and appropriate db

spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/{{your-db-name}}
spring.datasource.username={{your postgres db username}}
spring.datasource.password ={{your postgres db password}}
```

Build the Project using Maven
```bash
mvn clean install
```

Run the application:
```bash
mvn spring-boot:run
```
The service will be available on http://localhost:8080

Postman Documentation:- https://documenter.getpostman.com/view/19608895/2s9YyqjiEs

## Usage
The service exposes several REST endpoints:

1. SignUp:

`POST v1/api/authentication/signup`

Payload:
```bash
{
    "username": "adeife",
    "password": "uiehdgviue"
}
```

2. Load Drone with Medication:

`POST v1/api/authentication/login`
```bash
{
    "username": "adeife",
    "password": "uiehdgviue"
}
```
3. Verify Card Scheme:

`GET v1/api/card-scheme/verify/{bin}`

4. Get Stats:

`GET v1/api/card-scheme/stats`

## Running the tests
Execute the following command to run the unit tests:

```bash
mvn test
```
## Built With
- Spring Boot - The web framework used
- Maven - Dependency Management

## Authors
- [Joshua Niji](https://www.linkedin.com/in/joshua-adeniji/)  

