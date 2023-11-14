package org.learning.guide.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonSummaryCacheStatisticsProvider {}
//implements CacheStatisticsProvider<PersonSummaryCache> {
//
//  @Override
//  public CacheStatistics getCacheStatistics(CacheManager cacheManager, PersonSummaryCache personSummaryCache) {
//    DefaultCacheStatistics defaultCacheStatistics = new DefaultCacheStatistics();
//    defaultCacheStatistics.setSize(personSummaryCache.getCacheSize());
//    defaultCacheStatistics.setGetCacheCounts(personSummaryCache.getCacheHits(), personSummaryCache.getCacheMisses());
//    return defaultCacheStatistics;
//  }
//}
