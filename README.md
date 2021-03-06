# Serverless BookStore Spring Boot App #

Spring Boot based implementation of a lean BookStore Serverless Application based on Spring Boot.
It uses an in-memory database to handle CRUD operations of the bookStore.

### Challenge
Question 1

Please go through the API spec and validate the APIs. If the API endpoints are not according to standards please modify and provide your justification in your answer. 
For your reference these are the HTTP REST end points in general.

### Solution
The HTTP Methods provided in the json spec : [bookstore-api-v1-spec.json](src/main/resources/bookstore-api-v1-spec.json) did not properly define the specifications for the PATCH, PUT and DELETE operations.

Question 2 

Consequently, to implement Question 2 the API Definition Swagger file was created to Address issues found in the json spec : [serverless-bookstore-api-v1.yaml](serverless-bookstore-api-v1.yaml)


# Microservice Implementation in Spring Boot deployed with AWS Elastic Beanstalk


### Create a book
POST http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books
payload:
{"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}
response:
201 {"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Get a book by id 
GET http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books/51
response:
{"bookId":51,"title":"Title51","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Get all books
GET http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books
response:
200 OK
[{"bookId":55,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}]

### Update a book
PUT http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books/55
payload:
{"bookId":55,"title":"Title55","publisher":"Bookstore","year":"2010","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}
response:
200 OK
{"bookId":55,"title":"Title55","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Patch a book
PUT http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books/55
payload:
{"bookId":55,"title":"Title50","price":250.0}
response:
200 OK
{"bookId":55,"title":"Title55","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Search By Author
GET http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com//books/search?q=Lambeth%20North51
response:
[{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}]

### Search By Title
GET http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books/search?q=Title51
response:
200 OK
[{"bookId":51,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}]

### Delete
DELETE http://bookstoreapi-env.auwr3xuf3h.eu-west-1.elasticbeanstalk.com/books/Title50
response:
200 OK
Deleted:99

## POSTMAN Published API Requests
The POSTMAN published API can be accessed publicly  https://documenter.getpostman.com/view/3255604/RWTmwJmg ( You can click Run in PostMan) to automatically run each of the API service endpoints.
![Published Postman Screenshot](PostMan_APIScreenshot.png?raw=true "Published Postman Screenshot")
