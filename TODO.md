# Overall Framework
- spring boot
- @RestController, @Repository crud endpoints with a postgres database
- WebClient - call out to another service
- @Cacheable
- @SpringBootTest Component Tests
- @KafkaListener/@EmbeddedKafka??
- Spring boot actuator health/info/etc, Application specific healthcheck
- micrometer/observations
- https://openlibrary.org/dev/docs/api/authors

# Service Design

@RestController 
- GET /books
  - return all books from our database
- GET /books/{id}
  - return a books from our database
- POST /books - add book to our database (implement check before insert to verify if the author is real)
  - check if author exists by calling https://openlibrary.org/search/authors.json?q=rowling (use @Cacheable)
  - if author exists store data in our database
  - https://openlibrary.org/developers/api
- PUT /books/{id}
  - modify a book in our database
- DELETE /books/{id}
  - delete a book in our database

@KafkaListener
- author change
- purge from cache
- purge from postgres


# Project Layout 

## controller
- BooksController
- BooksService


## client
- OpenLibraryClient

## consumer
- AuthorChangeListener

## model
- Books
- OpenLibraryAuthor

## repository
- BooksRepository