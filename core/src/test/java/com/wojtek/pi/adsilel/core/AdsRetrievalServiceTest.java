package com.wojtek.pi.adsilel.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.parse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AdsRetrievalServiceTest {

    private static final LocalDateTime FROM = parse("2007-12-03T10:15:30");
    private static final LocalDateTime TO = parse("2007-12-03T10:16:30");

    @Mock
    private RawAdsRetriever rawAdsRetriever;
    @Mock
    private List<RawAd> rawAds;
    @Mock
    private RawAdsDao rawAdsDao;

    private AdsRetrievalService testedObject;

    @BeforeEach
    void setUp() {
        initMocks(this);
        testedObject = new AdsRetrievalService(rawAdsRetriever, rawAdsDao);
    }

    @Test
    void shouldRetrieveAdsWhenRefreshCalled() {
        testedObject.refreshAds(FROM, TO);

        verify(rawAdsRetriever).fetchAds(FROM, TO);
    }

    @Test
    void shouldSaveRawAdsToDao() {
        when(rawAdsRetriever.fetchAds(FROM, TO)).thenReturn(rawAds);

        testedObject.refreshAds(FROM, TO);

        verify(rawAdsDao).save(rawAds);
    }
}