package org.learning.guide.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.learning.guide.domain.PersonSummaryCache;
import org.learning.guide.service.PersonSummary;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonSummaryControllerWithCachingTest extends BaseComponent {

  @Autowired
  PersonSummaryCache personSummaryCache;

  @Test
  public void getPersonCached() {
    String personId = createDefaultPersonForTesting();

    long cacheHits = personSummaryCache.getCacheHits();

    testRestTemplate.getForEntity("/summary/{personId}", PersonSummary.class, personId);

    assertThat(personSummaryCache.getCacheHits()).isEqualTo(cacheHits);

    ResponseEntity<PersonSummary> responseEntity = testRestTemplate
        .getForEntity("/summary/{personId}", PersonSummary.class, personId);

    assertThat(personSummaryCache.getCacheHits()).isEqualTo(cacheHits + 1);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody())
        .hasFieldOrPropertyWithValue("firstName", "Bob")
        .hasFieldOrPropertyWithValue("lastName", "Dole");
  }

  @Test
  public void getPersonAfterDeletion() {
    String personId = createDefaultPersonForTesting();

    long cacheSize = personSummaryCache.getCacheSize();

    testRestTemplate.getForEntity("/summary/{personId}", PersonSummary.class, personId);

    assertThat(personSummaryCache.getCacheSize()).isGreaterThan(cacheSize);

    deleteTreePerson(personId);
    testRestTemplate.delete("/summary/{personId}", personId);

    assertThat(personSummaryCache.getCacheSize()).isEqualTo(cacheSize);

    final ResponseEntity<PersonSummary> getAfterDeleteResponseEntity =
        testRestTemplate.getForEntity("/summary/{personId}", PersonSummary.class, personId);

    assertThat(getAfterDeleteResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

}
