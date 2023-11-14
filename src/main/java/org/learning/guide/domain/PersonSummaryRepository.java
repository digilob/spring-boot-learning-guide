package org.learning.guide.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonSummaryRepository extends JpaRepository<PersonSummaryEntity, String> {
}
