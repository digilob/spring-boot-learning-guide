package org.learning.guide.controller;

import org.learning.guide.controller.schema.Author;
import org.learning.guide.controller.schema.Authors;
import org.learning.guide.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

public class AuthorsController {

  private AuthorService authorService;

  @Autowired
  public AuthorsController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
    Author createdAuthor = authorService.createAuthor(author);
    return ResponseEntity
            .created(URI.create(String.valueOf(createdAuthor.getAuthorId())))
            .body(createdAuthor);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.OK)
  public Authors getAuthors() {
    return authorService.getAllAuthors();
  }

  @GetMapping(value = "/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.OK)
  public Author getAuthor(@PathVariable("authorId") Long authorId) {
    return authorService.getAuthor(authorId);
  }

  @PutMapping(value = "/{authorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void updateAuthor(@RequestBody Author author,
                           @PathVariable("authorId") Long authorId) {
      authorService.updateAuthor(authorId, author);
  }

  @DeleteMapping(value = "/{authorId}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteAuthor(@PathVariable("authorId") Long authorId) {
    authorService.deleteAll();
  }
}
