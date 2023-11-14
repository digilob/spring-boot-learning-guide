package org.learning.guide.client;

import org.learning.guide.resource.TfPerson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TfPersonClient {

  private final SessionHelper sessionHelper;

  public TfPersonClient(@Value("${url.base}") String rootUri,
                        SessionHelper sessionHelper) {
    this.sessionHelper = sessionHelper;
  }

  public TfPerson getPerson(String personId) {
    return new TfPerson();
  }

}
