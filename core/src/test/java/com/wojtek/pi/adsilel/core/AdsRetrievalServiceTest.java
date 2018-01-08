package com.wojtek.pi.adsilel.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.parse;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdsRetrievalServiceTest {

    private static final LocalDateTime FROM = parse("2007-12-03T10:15:30");
    private static final LocalDateTime TO = parse("2007-12-03T10:16:30");

    @Mock
    private RawAdsRetriever rawAdsRetriever;
    @Mock
    private List<RawAd> rawAds;
    @Mock
    private RawAdsDao rawAdsDao;

    private AdsRetrievalService testedObject;

    @Before
    public void setUp() {
        initMocks(this);
        testedObject = new AdsRetrievalService(rawAdsRetriever, rawAdsDao);
    }

    @Test
    public void shouldRetrieveAdsWhenRefreshCalled() {
        testedObject.refreshAds(FROM, TO);

        verify(rawAdsRetriever).fetchAds(FROM, TO);
    }

    @Test
    public void shouldSaveRawAdsToDao() {
        when(rawAdsRetriever.fetchAds(FROM, TO)).thenReturn(rawAds);

        testedObject.refreshAds(FROM, TO);

        verify(rawAdsDao, times(1)).save(rawAds);
    }
}