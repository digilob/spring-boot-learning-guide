package org.learning.guide.component;

import org.learning.guide.ApplicationTestConfig;
import org.learning.guide.PostgresqlDockerContainer;
import org.learning.guide.UrlFactoryForTesting;
import org.learning.guide.resource.OpenLibraryDocResource;
import org.learning.guide.resource.OpenLibraryResource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

  public String createDefaultAuthor() {
    // ToDo: Add this author to a mock for openLibrary
//    OpenLibraryResource openLibraryResource = createOpenLibraryResource("J. K. Rowling");
//    ResponseEntity<String> response = testRestTemplate
//            .postForEntity("search/authors.json?q=J.%20K.%20Rowling&limit=1", openLibraryResource, String.class);
    return "J. K. Rowling";
  }

  private OpenLibraryResource createOpenLibraryResource(String authorName) {
    OpenLibraryResource openLibraryResource = new OpenLibraryResource();

    openLibraryResource.setNumFound(1);
    openLibraryResource.setDocs(createDocs(authorName));

    return openLibraryResource;
  }

  private List<OpenLibraryDocResource> createDocs(String authorName) {
    OpenLibraryDocResource openLibraryDocResource = new OpenLibraryDocResource();
    openLibraryDocResource.setKey("OL23919A");
    openLibraryDocResource.setType("author");
    openLibraryDocResource.setName(authorName);
    openLibraryDocResource.setBirthDate("31 July 1965");
    openLibraryDocResource.setTopWork("Harry Potter and the Philosopher's Stone");
    openLibraryDocResource.setWorkCount(474);
    return List.of(openLibraryDocResource);
  }
}

