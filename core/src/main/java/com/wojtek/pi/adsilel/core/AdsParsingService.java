package com.wojtek.pi.adsilel.core;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

class AdsParsingService {
    private final RawAdsDao rawAdsDao;
    private final RawAdParser rawAdParser;
    private final ParsedAdsDao parsedAdsDao;

    AdsParsingService(RawAdsDao rawAdsDao, RawAdParser rawAdParser, ParsedAdsDao parsedAdsDao) {
        this.rawAdsDao = rawAdsDao;
        this.rawAdParser = rawAdParser;
        this.parsedAdsDao = parsedAdsDao;
    }

    void parseAdsInRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<RawAd> adsBetween = rawAdsDao.getAdsBetween(dateFrom, dateTo);

        List<ParsedAd> parsedAds = adsBetween.stream().map(rawAdParser::parse).collect(toList());

        parsedAdsDao.saveAll(parsedAds);

    }
}
