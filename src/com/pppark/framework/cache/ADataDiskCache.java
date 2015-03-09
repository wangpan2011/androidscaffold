package com.pppark.framework.cache;

import java.util.HashMap;

public abstract class ADataDiskCache {
    public final static HashMap<String, Boolean> mArrFlags = new HashMap<String, Boolean>();

    String mUrl;

    ADataDiskCache(String pUrl) {
        this.mUrl = pUrl;
    }

    abstract String getDataKey();

    public abstract void saveToCache(String pData);

    public abstract String getFromCache();

    public abstract String getLastModified();

    public abstract void saveLastModified(String pLastModified);

    public abstract boolean isLoaded();

    public abstract void setLoaded();
}
