package org.learning.guide.client;

import org.learning.guide.resource.OpenLibraryResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

@Component
public class OpenLibraryClient {
  private final RestTemplate restTemplate;
//  https://openlibrary.org/search/authors.json?q=J.%20K.%20Rowling&limit=1

  public OpenLibraryClient(@Value("${openlibrary.root.url}") String rootUri, RestTemplateBuilder restTemplateBuilder) {
    restTemplate = restTemplateBuilder.rootUri(rootUri).build();
  }

  public OpenLibraryResource findAuthor(String authorName) {
    UriBuilder uriBuilder = new DefaultUriBuilderFactory().builder()
            .path("/search/authors.json")
            .queryParam("q", authorName)
            .queryParam("limit", 1);

    return restTemplate.getForObject(uriBuilder.build(), OpenLibraryResource.class);
  }

}
