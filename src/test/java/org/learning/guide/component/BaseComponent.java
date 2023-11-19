package org.learning.guide.component;

import org.learning.guide.ApplicationTestConfig;
import org.learning.guide.PostgresqlDockerContainer;
import org.learning.guide.UrlFactoryForTesting;
import org.learning.guide.resource.TfPerson;
import org.learning.guide.resource.TfPersonSummary;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ApplicationTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
  "url.base=http://localhost:8080",
  "url.test=http://localhost:8080",
  "url.mgmt=http://localhost:8080"
})
public abstract class BaseComponent {

  PostgresqlDockerContainer postgresqlDockerContainer = PostgresqlDockerContainer.getInstance();

  @Autowired
  TestRestTemplate testRestTemplate;

  @Autowired
  UrlFactoryForTesting urlFactoryForTesting;

  public String createDefaultPersonForTesting() {
    ResponseEntity<String> response = testRestTemplate.postForEntity("/tf/person", createTfPerson("Bob", "Dole"), String.class);
    return response.getHeaders().get("X-Entity-ID").get(0);
  }

  void deleteTreePerson(String personId) {
    testRestTemplate.delete("/tf/person/{personId}", personId);
  }

  private TfPerson createTfPerson(String firstName, String lastName) {
    TfPerson tfPerson = new TfPerson();
    TfPersonSummary summary = createSummary(firstName, lastName);
    tfPerson.setSummary(summary);
    return tfPerson;
  }

  private TfPersonSummary createSummary(String first, String lastName) {
    return new TfPersonSummary(first, lastName);
  }
}

