package org.learning.guide.web;

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
    assertThat(responseEntity.getBody().getInfoDetails())
      .as("Spring-Boot Microbadge info details should be present")
      .hasFieldOrPropertyWithValue("name", "spring-boot-microbadge")
      .hasFieldOrPropertyWithValue("description", "Demo project for Spring Boot")
      .hasFieldOrPropertyWithValue("version", "0.0.1-SNAPSHOT");
  }

  private String getInfoUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "info";
  }
}
