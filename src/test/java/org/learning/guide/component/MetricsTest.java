package org.learning.guide.component;

import org.learning.guide.schema.Metric;
import org.learning.guide.schema.Metrics;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MetricsTest extends BaseComponent {

  @Test
  public void getMetricsTest() {
    ResponseEntity<Metrics> responseEntity = testRestTemplate.getForEntity(getMetricsUrl(), Metrics.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().getNames()).isNotNull();
  }

  @Test
  public void getHttpServerRequestsMetricTest() {
    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl("no-cache");
    HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);
    testRestTemplate.exchange(getHelloWorldUrl(), HttpMethod.GET, httpEntity, String.class);
    testRestTemplate.exchange(getHelloWorldUrl(), HttpMethod.GET, httpEntity, String.class);
    testRestTemplate.exchange(getHelloWorldUrl(), HttpMethod.GET, httpEntity, String.class);

    ResponseEntity<Metric> responseEntity = testRestTemplate.getForEntity(getHttpServerRequestsMetricUrl(), Metric.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().getName()).isEqualTo("http.server.requests");
  }

  @Test
  public void getPrometheusMetricTest() {
    ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(getPrometheusUrl(), String.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotBlank();
  }

  private String getMetricsUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "/actuator/metrics";
  }

  private String getHttpServerRequestsMetricUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "/actuator/metrics/http.server.requests";
  }

  private String getPrometheusUrl() {
    return urlFactoryForTesting.getMgmtUrl() + "/actuator/prometheus";
  }

  private String getHelloWorldUrl() {
    return urlFactoryForTesting.getTestUrl() + "/hello";
  }
}
