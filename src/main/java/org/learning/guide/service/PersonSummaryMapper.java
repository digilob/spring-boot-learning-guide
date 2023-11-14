package org.learning.guide.service;


import org.learning.guide.resource.TfPerson;

public class PersonSummaryMapper {

  public PersonSummary map(TfPerson tfPerson) {
    PersonSummary personSummary = new PersonSummary();

    String[] names = tfPerson.getSummary().getName().split(" ");
    personSummary.setFirstName(names[0]);
    personSummary.setLastName(names[1]);

    return personSummary;
  }

}
