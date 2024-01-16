package org.learning.guide.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenLibraryDocResource {
    @JsonProperty("key")
    String key;
    @JsonProperty("type")
    String type;
    @JsonProperty("name")
    String name;

    @JsonProperty("birth_date")
    String birthDate;
    @JsonProperty("top_work")
    String topWork;
    @JsonProperty("work_count")
    Integer workCount;
}
