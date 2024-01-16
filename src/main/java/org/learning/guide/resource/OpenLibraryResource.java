package org.learning.guide.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenLibraryResource {
    Integer numFound;
    List<OpenLibraryDocResource> docs;
}
