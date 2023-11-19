package org.learning.guide.component;

import org.learning.guide.schema.HealthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthTest extends BaseComponent {

  @Test
  public void getHealthTest() {
    ResponseEntity<HealthResponse> responseEntity = testRestTemplate.getForEntity(getHealthcheckUrl(), HealthResponse.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().getStatus()).isEqualTo(Status.UP);
  }

  @Test
  public void getDiskSpaceTest() {
    ResponseEntity<HealthResponse> responseEntity = testRestTemplate.getForEntity(getHealthcheckUrl(), HealthResponse.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    HealthResponse compositHealth = responseEntity.getBody();
    assertThat(compositHealth.getStatus()).isEqualTo(Status.UP);
    assertThat(compositHealth.getComponents().get("db").getStatus()).isEqualTo(Status.UP);
    assertThat(compositHealth.getComponents().get("diskSpace").getStatus()).isEqualTo(Status.UP);
  }

  private String getHealthcheckUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "/actuator/health";
  }
}
