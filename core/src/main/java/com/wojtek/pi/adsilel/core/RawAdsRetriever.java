package com.wojtek.pi.adsilel.core;

import java.time.LocalDateTime;
import java.util.List;

public interface RawAdsRetriever {
    List<RawAd> fetchAds(LocalDateTime from, LocalDateTime to);
}
