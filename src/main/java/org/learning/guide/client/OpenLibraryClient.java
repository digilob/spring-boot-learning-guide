package org.learning.guide.client;

import org.learning.guide.exception.OlAuthorNotFoundException;
import org.learning.guide.resource.OpenLibraryResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenLibraryClient {
  private RestTemplate restTemplate;
//  https://openlibrary.org/search/authors.json?q=J.%20K.%20Rowling&limit=1

  public OpenLibraryClient(@Value("${openlibrary.root.url}") String olRootUri, RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.rootUri(olRootUri).build();
  }
  public OpenLibraryResource findAuthor(String authorName) {
    String searchUrl = UriComponentsBuilder.fromPath("/search/authors.json")
        .queryParam("q", authorName)
        .queryParam("limit", 1)
         .build()
         .toString();
    try {
      return restTemplate.getForObject(searchUrl, OpenLibraryResource.class);
    } catch (HttpClientErrorException e) {
      throw new OlAuthorNotFoundException("Author not found");
    } catch (RestClientException e) {
      throw new RuntimeException("Something went wrong");
    }
  }
}
