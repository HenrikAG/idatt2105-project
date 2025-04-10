# idatt2105-project
Voluntary project in subject IDATT2105 full-stack application development

## Introduction
### Project description
Create a website where users are able to put products up for sale, and other users can buy them.

### Setup
We havent set up for deployment for docker yet.

To follow this setup tutorial you will need:
- Maven
- Nodejs
- Java
- Available ports: 8080 and 5173

### Backend
1. Clone the project
2. run cd backend
3. Run mvn spring-boot:run

The backend will now be running on post 8080.
The default admin password username is:
- Password: admin
- Username: admin
There is also a premade user, items and categories.
The username and password of the user is:
- Password: password
- Username: username

### Frontend
Star from the project folder and run the following commands:
1. cd frontend
2. npm run build (currently not working just use npm run dev instead)
3. Should find build file of project in frontend ./dist folder (wont wont work since build not working)
The frontend should then run on port 5173

## Run tests
### Backend
From backend folder run
- mvn clean test
For coverage run:
- mvn verify
Should find report in: target/site/jacoco/index.html

### Frontend 
Cypress:
- Run the project
- npx cypress run
Unit tests (vitest)
- npx vitest
(Currently dont have any tests for frontend)

## Technologies
- Vue 3
- Maven
- Java
- Spring Boot
- Spring Data JPA
- H2 Database

## Documentation
- [Swagger]{http://localhost:8080/swagger-ui/index.html}
- JavaDoc

## Functionality
### Not logged in
- Log in
- De registered categories and products

### User
- All functionality as not logged in
- Post products
- Delete own products
- Message other users
- Log out
- Delete user

### Admin
- All functionality of user
- Add categories
- Delete categories (Not working)
- Delete other user's products
- Delete other users
