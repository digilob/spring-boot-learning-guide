package org.learning.guide.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;

@Table("books")
@Accessors(fluent = true)
@Data
@Slf4j
public class BookEntity implements Serializable {

  @Id
  private Long id;

  @Column("book_title")
  private String bookTitle;

  @Column("book_category")
  private String bookCategory;

  @Column("publish_date")
  private Instant publishDate;

  @Column("author_id")
  private Long authorId;

  @Column("created_timestamp")
  private Instant createdTimestamp;

  @Column("updated_timestamp")
  private Instant updatedTimestamp;
}

