package org.learning.guide.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Entity(name = "person_summary")
@Table(name = "persons")
@Accessors(fluent = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonSummaryEntity implements Serializable {

  public PersonSummaryEntity(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorsSequenceGenerator")
  @SequenceGenerator(name = "authorsSequenceGenerator", sequenceName = "SEQ_AUTHORS_ID", allocationSize = 1)
  @Column(name = "person_id")
  private Long personId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
}
