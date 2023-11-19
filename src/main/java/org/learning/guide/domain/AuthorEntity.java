package org.learning.guide.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;

@Table("authors")
@Accessors(fluent = true)
@Data
@Slf4j
public class AuthorEntity implements Serializable {

  @Id
  private Long id;

  @Column("author_first_name")
  private String authorFirstName;

  @Column("author_last_name")
  private String authorLastName;

  @Column("created_timestamp")
  private Instant createdTimestamp;

  @Column("updated_timestamp")
  private Instant updatedTimestamp;
}
