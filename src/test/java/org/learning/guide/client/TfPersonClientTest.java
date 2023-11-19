package org.learning.guide.client;

import org.learning.guide.resource.TfPerson;
import org.learning.guide.component.BaseComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class TfPersonClientTest extends BaseComponent {

  @Autowired
  TfPersonClient tfPersonClient;

  @Test
  public void testPersonClientReturnsCorrectly() {
    String personId = createDefaultPersonForTesting();
    TfPerson person = tfPersonClient.getPerson(personId);

    assertThat(person.getPersonId())
        .as("Lets use RestTemplate to get the data back from TF")
        .isEqualTo(personId);
  }
}
