package com.wojtek.pi.adsilel.core;

import java.time.LocalDateTime;
import java.util.List;

class AdsRetrievalService {

    private final RawAdsRetriever rawAdsRetriever;
    private final RawAdsDao rawAdsDao;

    AdsRetrievalService(RawAdsRetriever rawAdsRetriever, RawAdsDao rawAdsDao) {
        this.rawAdsRetriever = rawAdsRetriever;
        this.rawAdsDao = rawAdsDao;
    }

    void refreshAds(LocalDateTime from, LocalDateTime to) {
        List<RawAd> rawAds = rawAdsRetriever.fetchAds(from,to);
        rawAdsDao.save(rawAds);
    }
}
