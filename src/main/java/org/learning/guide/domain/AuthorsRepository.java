package org.learning.guide.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsRepository extends CrudRepository<AuthorEntity, Long> {
}
