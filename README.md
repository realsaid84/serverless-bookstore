# Serverless BookStore Spring Boot App #

Spring Boot based implementation of a lean BookStore Serverless Application based on Spring Boot.
It uses an in-memory database to handle CRUD operations of the bookStore.




### Create a book
POST http://localhost:8080/books
payload:
{"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}
response:
201 {"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Get a book by id 
GET http://localhost:8080/books/99
response:
{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Get all books
GET http://localhost:8080/books
response:
200 OK
[{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}]

### Update a book
PUT http://localhost:8080/books/99
payload:
{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"2010","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}
response:
200 OK
{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Patch a book
PUT http://localhost:8080/books/99
payload:
{"bookId":99,"title":"Title50","price":250.0}
response:
200 OK
{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}

### Search By Author
GET http://localhost:8080/books/search?q=Lambeth%20North50
response:
[{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}]

### Search By Title
GET http://localhost:8080/books/search?q=Title50
response:
200 OK
[{"bookId":99,"title":"Title50","publisher":"Bookstore","year":"19050","price":250.0,"authors":[{"authorId":100,"name":"Lambeth North50","placeOfBirth":"EC50"}]}]

### Delete
DELETE http://localhost:8080/books/Title50
response:
200 OK
Deleted:99

## POSTMAN Published API Requests
The POSTMAN published API can be accessed publicly  https://documenter.getpostman.com/view/3255604/RWTmwJmg
