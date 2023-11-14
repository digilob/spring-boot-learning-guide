package org.learning.guide.resource;

public class TfPersonSummary {
    private String firstName;
    private String lastName;

    public TfPersonSummary(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Get the person name
    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }
}
