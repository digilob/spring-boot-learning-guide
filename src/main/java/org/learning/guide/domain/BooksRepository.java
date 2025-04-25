package org.learning.guide.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends CrudRepository<BookEntity, Long> {
    Optional<BookEntity> findById(Long bookId);
}
