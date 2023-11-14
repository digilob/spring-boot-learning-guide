package org.learning.guide.domain;

import org.learning.guide.service.PersonSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PersonSummaryCache implements Cache {

  private static final String PERSON_SUMMARY_CACHE = "personSummaryCache";

  private final AtomicLong hits = new AtomicLong();

  private final AtomicLong misses = new AtomicLong();

  private final PersonSummaryRepository repository;
  private final String dynamoDbTableName;

  public PersonSummaryCache(PersonSummaryRepository repository, @Value("${DYNAMODB_TABLE_NAME}") String dynamoDbTableName) {
    this.repository = repository;
    this.dynamoDbTableName = dynamoDbTableName;
  }

  @Override
  public String getName() {
    return PERSON_SUMMARY_CACHE;
  }

  @Override
  public Object getNativeCache() {
    return PERSON_SUMMARY_CACHE;
  }

  @Override
  public ValueWrapper get(Object key) {
    Optional<PersonSummaryEntity> personSummary = repository.findById((String) key);

    if (personSummary.isPresent()) {
      addCacheMiss();
      return null;
    } else {
      addCacheHit();
      PersonSummaryEntity personSummaryEntity = personSummary.get();
      return new SimpleValueWrapper(new PersonSummary(personSummaryEntity.firstName(), personSummaryEntity.lastName()));
    }
  }

  @Override
  public <T> T get(Object key, Class<T> aClass) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public <T> T get(Object key, Callable<T> callable) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public void put(Object key, Object value) {
//    PersonSummary personSummary = (PersonSummary) value;
//    PersonSummaryEntity personSummaryEntity = new PersonSummaryEntity((String) key, personSummary.getFirstName(), personSummary.getLastName());
//    repository.save(personSummaryEntity);
  }

  @Override
  public ValueWrapper putIfAbsent(Object key, Object value) {
    put(key ,value);
    PersonSummary personSummary = (PersonSummary) value;
    return new SimpleValueWrapper(new PersonSummary(personSummary.getFirstName(), personSummary.getLastName()));
  }

  @Override
  public void evict(Object key) {
    repository.deleteById((String) key);
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Method not implemented");
  }

  public long getCacheSize() {
//    return amazonDynamoDB.describeTable(dynamoDbTableName).getTable().getItemCount();
    return 5;
  }

  public long getCacheHits() {
    return hits.get();
  }

  public long getCacheMisses() {
    return misses.get();
  }

  private void addCacheHit() {
    hits.incrementAndGet();
  }

  private void addCacheMiss() {
    misses.incrementAndGet();
  }

}
