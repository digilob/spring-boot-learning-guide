package org.learning.guide.controller.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  private Long   id;
  private String bookTitle;
  private String bookCategory;
  private Instant publishDate;
  private Long authorId;
}
