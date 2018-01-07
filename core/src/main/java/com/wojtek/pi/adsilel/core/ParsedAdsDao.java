package com.wojtek.pi.adsilel.core;

import java.util.List;

public interface ParsedAdsDao {
    void saveAll(List<ParsedAd> listOfRawAds);
}
