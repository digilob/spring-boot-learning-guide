package org.learning.guide.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoDbHealthTestResponse extends BaseComponent {

  @Test
  public void getDynamoDbHealth() {
    ResponseEntity<CompositeHealth> responseEntity = testRestTemplate.getForEntity(getHealthcheckUrl(), CompositeHealth.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    assertThat(responseEntity.getBody().getDynamo())
//     .as("dynamodb information should be present in the healthcheck")
//     .isNotNull();
//    assertThat(responseEntity.getBody().getDynamo().getStatus().getCode()).isEqualTo("UP");
//    assertThat(responseEntity.getBody().getDynamo().getItemCount()).isGreaterThanOrEqualTo(0);
//    assertThat(responseEntity.getBody().getDynamo().getTableSizeInBytes()).isGreaterThanOrEqualTo(0);
  }

  private String getHealthcheckUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "/healthcheck/heartbeat";
  }

}
