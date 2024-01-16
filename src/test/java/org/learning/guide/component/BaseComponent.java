package org.learning.guide.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.guide.ApplicationTestConfig;
import org.learning.guide.PostgresqlDockerContainer;
import org.learning.guide.UrlFactoryForTesting;
import org.learning.guide.resource.OpenLibraryDocResource;
import org.learning.guide.resource.OpenLibraryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {ApplicationTestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = BaseComponent.MyInitializer.class)
@AutoConfigureWireMock(port = 0)
public abstract class BaseComponent {

  PostgresqlDockerContainer postgresqlDockerContainer = PostgresqlDockerContainer.getInstance();

  @Autowired
  TestRestTemplate testRestTemplate;

  @Autowired
  UrlFactoryForTesting urlFactoryForTesting;

  @Autowired
  ObjectMapper objectMapper;

  public String createDefaultAuthor() {
    OpenLibraryResource openLibraryResource = createOpenLibraryResource("J. K. Rowling");
      try {
        stubFor(
                WireMock.get(WireMock.urlEqualTo("/search/authors.json?q=J.%20K.%20Rowling&limit=1"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(objectMapper.writeValueAsString(openLibraryResource))));
      } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
      }
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

  @Configuration
  static class MyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertyValues.of("url.base=http://localhost:8080" ).applyTo(applicationContext);
      TestPropertyValues.of("url.test=http://localhost:8080" ).applyTo(applicationContext);
      TestPropertyValues.of("url.mgmt=http://localhost:8080" ).applyTo(applicationContext);

      TestPropertyValues.of("openlibrary.root.url=http://localhost:${wiremock.server.port}").applyTo(applicationContext);
    }
  }

}

