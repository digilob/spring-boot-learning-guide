package org.learning.guide.controller;

import org.learning.guide.controller.schema.Book;
import org.learning.guide.controller.schema.Books;
import org.learning.guide.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/books")
public class BooksController {

  private BookService bookService;


  @Autowired

  public BooksController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity createBook(@RequestBody Book book) {
    Long bookId = bookService.createBook(book);
    return ResponseEntity.created(URI.create(String.valueOf(bookId))).build();
  }

  @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.OK)
  public Books getBooks() {
    return new Books(bookService.findAllBooks());
  }

  public Book getBook(@PathVariable("bookId") Long bookId) {
    return bookService.getBook(bookId);
  }

  @PutMapping(value = "/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void updateBook(@RequestBody Book book,
                         @PathVariable("bookId") Long bookId) {
    bookService.updateBook(bookId, book);
  }

  @DeleteMapping(value = "/{bookId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable("bookId") Long bookId) {
    bookService.deleteBook(bookId);
  }

}
