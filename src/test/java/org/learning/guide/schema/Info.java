package org.learning.guide.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Info {
  @JsonProperty(value = "app")
  private InfoDetails infoDetails;

  @JsonProperty(value = "java")
  private JavaDetails javaDetails;

  @Getter
  @Setter
  public static class InfoDetails {
    String name;
    String description;
    String version;
  }

  @Getter
  @Setter
  public static class JavaDetails {
    String version;
  }
}
