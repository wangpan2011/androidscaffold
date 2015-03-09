package com.pppark.framework.data;

import com.google.xlgson.JsonDeserializer;

@SuppressWarnings("rawtypes")
public class GsonTypeAdapterPair {

    public Class type;

    public JsonDeserializer typeAdapter;

    public GsonTypeAdapterPair(Class type, JsonDeserializer typeAdapter){
        this.type = type;
        this.typeAdapter = typeAdapter;
    }
}
