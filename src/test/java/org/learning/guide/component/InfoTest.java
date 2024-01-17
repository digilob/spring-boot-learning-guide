package org.learning.guide.component;

import org.learning.guide.schema.Info;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class InfoTest extends BaseComponent {

  @Test
  public void getInfoTest() {
    ResponseEntity<Info> responseEntity = testRestTemplate.getForEntity(getInfoUrl(), Info.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void getInfoTest_appDetails() {
    ResponseEntity<Info> responseEntity = testRestTemplate.getForEntity(getInfoUrl(), Info.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().getInfoDetails())
      .as("Spring-Boot Microbadge info details should be present")
      .hasFieldOrPropertyWithValue("name", "spring-boot-learning-guide")
      .hasFieldOrPropertyWithValue("description", "Spring Boot introductory learning guide")
      .hasFieldOrPropertyWithValue("version", "0.0.1-SNAPSHOT");
  }

  @Test
  public void getInfoTest_javaDetails() {
    ResponseEntity<Info> responseEntity = testRestTemplate.getForEntity(getInfoUrl(), Info.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().getJavaDetails())
      .as("Spring-Boot Microbadge java details should be present")
      .hasFieldOrProperty("version");
  }

  private String getInfoUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "/actuator/info";
  }
}
