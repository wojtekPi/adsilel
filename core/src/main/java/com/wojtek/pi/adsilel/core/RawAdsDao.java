package com.wojtek.pi.adsilel.core;

import java.time.LocalDateTime;
import java.util.List;

public interface RawAdsDao {

    void save(List<RawAd> rawAds);
    List<RawAd> getAdsBetween(LocalDateTime from, LocalDateTime to);

}
