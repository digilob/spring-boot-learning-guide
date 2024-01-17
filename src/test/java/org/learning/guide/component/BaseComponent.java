package org.learning.guide.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
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
import org.springframework.web.util.UriComponentsBuilder;

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

  @SneakyThrows
  public void mockOlAuthorRequest(String authorName) {
    OpenLibraryResource openLibraryResource = createOpenLibraryResource(authorName);
    String olSearchPath = UriComponentsBuilder.fromUriString("/search/authors.json")
            .queryParam("q", authorName)
            .queryParam("limit", 1)
            .build()
            .encode()
            .toUriString();

    try {
      stubFor(
              WireMock.get(WireMock.urlEqualTo(olSearchPath))
                      .willReturn(WireMock.aResponse()
                              .withStatus(200)
                              .withHeader("Content-Type", "application/json")
                              .withBody(objectMapper.writeValueAsString(openLibraryResource))));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @SneakyThrows
  public void mockOlAuthorRequestNoAuthor(String authorName) {
    OpenLibraryResource openLibraryResource = new OpenLibraryResource();
    openLibraryResource.setNumFound(0);
    openLibraryResource.setDocs(List.of());

    String olSearchPath = UriComponentsBuilder.fromUriString("/search/authors.json")
            .queryParam("q", authorName)
            .queryParam("limit", 1)
            .build()
            .encode()
            .toUriString();

    try {
      stubFor(
              WireMock.get(WireMock.urlEqualTo(olSearchPath))
                      .willReturn(WireMock.aResponse()
                              .withStatus(200)
                              .withHeader("Content-Type", "application/json")
                              .withBody(objectMapper.writeValueAsString(openLibraryResource))));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private OpenLibraryResource createOpenLibraryResource(String authorName) {
    OpenLibraryResource openLibraryResource = new OpenLibraryResource();

    openLibraryResource.setNumFound(1);
    openLibraryResource.setDocs(createDocs(authorName));

    return openLibraryResource;
  }

  private List<OpenLibraryDocResource> createDocs(String authorName) {
    OpenLibraryDocResource openLibraryDocResource = new OpenLibraryDocResource();
    openLibraryDocResource.setKey(String.valueOf(authorName.hashCode()));
    openLibraryDocResource.setType("author");
    openLibraryDocResource.setName(authorName);
    openLibraryDocResource.setBirthDate("31 July 1965");
    openLibraryDocResource.setTopWork(authorName + " most popular book");
    openLibraryDocResource.setWorkCount(25);
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

