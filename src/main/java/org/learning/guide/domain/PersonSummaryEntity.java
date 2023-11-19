package org.learning.guide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

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
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorsSequenceGenerator")
//  @SequenceGenerator(name = "authorsSequenceGenerator", sequenceName = "SEQ_AUTHORS_ID", allocationSize = 1)
  @Column("person_id")
  private Long personId;

  @Column("first_name")
  private String firstName;

  @Column("last_name")
  private String lastName;
}
