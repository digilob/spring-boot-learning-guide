package org.learning.guide.service;

import org.learning.guide.controller.schema.Author;
import org.learning.guide.controller.schema.Authors;
import org.learning.guide.domain.AuthorEntity;
import org.learning.guide.domain.AuthorsRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AuthorService {
  private AuthorsRepository authorsRepository;

  public AuthorService(AuthorsRepository authorsRepository) {
    this.authorsRepository = authorsRepository;
  }

  @Transactional
  public Author createAuthor(Author author) {
    AuthorEntity authorEntity = authorsRepository.save(mapToEntity(author));
    return mapToSchema(authorEntity);
  }

  @Transactional
  public void deleteAll() {
    authorsRepository.deleteAll();
  }

  @Transactional
  public Author getAuthor(Long authorId) {
    AuthorEntity authorEntity = authorsRepository.findById(authorId).get();
    return mapToSchema(authorEntity);
  }

  @Transactional
  public Authors getAllAuthors() {
    Iterable<AuthorEntity> allAuthors = authorsRepository.findAll();
    List<Author> authorList = StreamSupport.stream(allAuthors.spliterator(), false)
            .map(this::mapToSchema)
            .collect(Collectors.toList());

    return new Authors(authorList);
  }

  @Transactional
  public void updateAuthor(Long authorId, Author author) {
    AuthorEntity authorEntity = authorsRepository.findById(authorId).get();
    authorEntity.authorFirstName(author.getFirstName());
    authorEntity.authorLastName(author.getLastName());
    authorEntity.updatedTimestamp(Instant.now());
    authorsRepository.save(authorEntity);
  }

  private AuthorEntity mapToEntity(Author author) {
    AuthorEntity authorEntity = new AuthorEntity();
    authorEntity.authorFirstName(author.getFirstName());
    authorEntity.authorLastName(author.getLastName());
    authorEntity.id(author.getAuthorId());
    return authorEntity;
  }

  private Author mapToSchema(AuthorEntity authorEntity) {
    Author author = new Author();
    author.setFirstName(authorEntity.authorFirstName());
    author.setLastName(authorEntity.authorLastName());
    author.setAuthorId(authorEntity.id());
    return author;
  }
}
