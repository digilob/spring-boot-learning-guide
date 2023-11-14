package org.learning.guide.resource;

public class TfPerson {
    String personId;
    TfPersonSummary summary;
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public TfPersonSummary getSummary() {
        return summary;
    }

    public void setSummary(TfPersonSummary summary) {
        this.summary = summary;
    }
}
