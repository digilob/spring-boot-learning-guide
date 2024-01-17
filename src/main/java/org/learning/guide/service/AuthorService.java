package org.learning.guide.service;

import org.learning.guide.client.OpenLibraryClient;
import org.learning.guide.controller.schema.Author;
import org.learning.guide.controller.schema.Authors;
import org.learning.guide.domain.AuthorEntity;
import org.learning.guide.domain.AuthorsRepository;
import org.learning.guide.exception.OlAuthorNotFoundException;
import org.learning.guide.resource.OpenLibraryResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AuthorService {
  private AuthorsRepository authorsRepository;

  private OpenLibraryClient openLibraryClient;

  public AuthorService(AuthorsRepository authorsRepository, OpenLibraryClient openLibraryClient) {
    this.authorsRepository = authorsRepository;
    this.openLibraryClient = openLibraryClient;
  }

  @Transactional
  public Author createAuthor(Author author) {
    verifyAuthorName(author);

    Instant now = Instant.now();
    AuthorEntity entity = mapToEntity(author);
    entity.createdTimestamp(now);
    entity.updatedTimestamp(now);

    AuthorEntity authorEntity = authorsRepository.save(entity);
    return mapToSchema(authorEntity);
  }

  private void verifyAuthorName(Author author) {
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
    authorEntity.authorName(author.getAuthorName());
    authorEntity.updatedTimestamp(Instant.now());
    authorsRepository.save(authorEntity);
  }

  private AuthorEntity mapToEntity(Author author) {
    AuthorEntity authorEntity = new AuthorEntity();
    authorEntity.authorName(author.getAuthorName());
    authorEntity.id(author.getAuthorId());
    return authorEntity;
  }

  private Author mapToSchema(AuthorEntity authorEntity) {
    Author author = new Author();
    author.setAuthorName(authorEntity.authorName());
    author.setAuthorId(authorEntity.id());
    return author;
  }
}
