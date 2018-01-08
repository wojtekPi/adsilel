package com.wojtek.pi.adsilel.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static java.time.LocalDateTime.parse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdsParsingServiceTest {

    private static final LocalDateTime DATE_TO = parse("2007-12-03T10:16:30");
    private static final LocalDateTime DATE_FROM = parse("2007-12-03T10:15:30");
    private AdsParsingService testedObject;

    @Mock
    private RawAdsDao rawAdsDao;
    @Mock
    private RawAdParser rawAdParser;
    @Mock
    private RawAd rawAd1;
    @Mock
    private RawAd rawAd2;
    @Mock
    private ParsedAd parsedAd1;
    @Mock
    private ParsedAd parsedAd2;
    @Mock
    private ParsedAdsDao parsedAdsDao;
    private ArrayList<RawAd> listOfRawAds;
    private ArrayList<ParsedAd> listOfParsedAds;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listOfRawAds = newArrayList(rawAd1, rawAd2);
        listOfParsedAds = newArrayList(parsedAd1, parsedAd2);
        testedObject = new AdsParsingService(rawAdsDao, rawAdParser, parsedAdsDao);
    }

    @Test
    public void shouldParseAllAdsInRange() {
        when(rawAdsDao.getAdsBetween(DATE_FROM, DATE_TO)).thenReturn(listOfRawAds);

        testedObject.parseAdsInRange(DATE_FROM, DATE_TO);

        verify(rawAdParser).parse(rawAd1);
        verify(rawAdParser).parse(rawAd2);
    }

    @Test
    public void shouldSaveParsedAdsToDao() {
        when(rawAdsDao.getAdsBetween(DATE_FROM, DATE_TO)).thenReturn(listOfRawAds);
        when(rawAdParser.parse(rawAd1)).thenReturn(parsedAd1);
        when(rawAdParser.parse(rawAd2)).thenReturn(parsedAd2);

        testedObject.parseAdsInRange(DATE_FROM, DATE_TO);

        verify(parsedAdsDao).saveAll(listOfParsedAds);
    }

}