package org.learning.guide.service;

import org.learning.guide.resource.TfPerson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/tf/person")
public class TfServiceForTesting {

  @Value("${url.base}")
  private String baseUrl;

  private static final AtomicLong nextId = new AtomicLong(100L);
  private final Map<String, TfPerson> keyValueStore = new ConcurrentHashMap<>();

  @RequestMapping(value = "/{personId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public TfPerson getPerson(@PathVariable(value = "personId") String personId) {
    return keyValueStore.get(personId);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity postPerson(@RequestBody TfPerson tfPerson) {
    String personId = generateId();
    tfPerson.setPersonId(personId);
    keyValueStore.put(personId, tfPerson);
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Entity-ID", personId);
    return new ResponseEntity<>(URI.create(baseUrl + "/tf/person/" + personId), headers, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
  public ResponseEntity deletePerson(@PathVariable(value = "personId") String personId) {
    keyValueStore.remove(personId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  private String generateId() {
    return String.valueOf(nextId.getAndIncrement());
  }
}
