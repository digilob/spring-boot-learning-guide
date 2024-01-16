package org.learning.guide.client;

import org.learning.guide.resource.OpenLibraryResource;
import org.learning.guide.component.BaseComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class OpenLibraryClientTest extends BaseComponent {

  @Autowired
  OpenLibraryClient openLibraryClient;

  @Test
  public void testOpenLibraryClientReturnsCorrectly() {
    String authorName = createDefaultAuthor();
    OpenLibraryResource openLibraryResource = openLibraryClient.findAuthor(authorName);

    assertThat(openLibraryResource.getNumFound())
        .as("Lets use RestTemplate to get the data back from TF")
        .isEqualTo(authorName);
  }
}
