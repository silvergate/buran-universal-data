package com.gmail.at.silvergate.buran.universalData.jsonImpl;

import com.gmail.at.silvergate.buran.universalData.IObject;
import com.gmail.at.silvergate.buran.universalData.IType;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class JsonObject implements IObject {

    private final com.google.gson.JsonObject data;
    private final Map<String, TypeAndKey> keysToTypeAndKeys = new HashMap<String, TypeAndKey>();

    public JsonObject() {
        this.data = new com.google.gson.JsonObject();
    }

    public JsonObject(com.google.gson.JsonObject backingData) {
        this.data = backingData;
        fromJsonToKeysToTypeAndKeysMap();
    }

    private void fromJsonToKeysToTypeAndKeysMap() {
        for (Map.Entry<String, JsonElement> entry : this.data.entrySet()) {
            final String jsonKey = entry.getKey();
            final TypeAndKey type = JsonBasics.fromJsonKeyToTypeAndKey(jsonKey);
            this.keysToTypeAndKeys.put(type.getKey(), type);
        }
    }

    @Override
    public boolean isDefined(String key) {
        return this.keysToTypeAndKeys.containsKey(key);
    }

    @Override
    public boolean undefine(String key) {
        if (!isDefined(key))
            return false;
        final TypeAndKey typeAndKey = this.keysToTypeAndKeys.get(key);
        final String jsonKey = JsonBasics.getTypeInfoAndKey(typeAndKey.getType(), key);
        this.data.remove(jsonKey);
        this.keysToTypeAndKeys.remove(key);
        return true;
    }

    @Override
    public Set<String> getDefinedKeys() {
        return Collections.unmodifiableSet(this.keysToTypeAndKeys.keySet());
    }

    @Override
    public Optional<IType<?>> getType(String key) {
        final TypeAndKey typeAndKey = this.keysToTypeAndKeys.get(key);
        if (typeAndKey == null)
            return Optional.absent();

        return (Optional<IType<?>>) Optional.of(((IType<?>) typeAndKey.getType()));
    }

    @Override
    public <T> Optional<T> as(String key, IType<T> type) {
        final Optional<IType<?>> realType = getType(key);
        if (!realType.isPresent())
            return Optional.absent();

        if (!realType.get().equals(type))
            return Optional.absent();

        final String jsonKey = JsonBasics.getTypeInfoAndKey(type, key);
        final JsonElement jsPrimitive = this.data.get(jsonKey);
        if (jsPrimitive == null)
            return Optional.absent();
        final T value = JsonBasics.fromPrimitive(jsPrimitive, type);
        if (value == null)
            Optional.absent();

        return Optional.of(value);
    }

    @Override
    public <T> void set(String key, IType<T> type, T value) {
        final JsonElement element = JsonBasics.fromJavaType(value, type);
        if (element == null)
            throw new IllegalArgumentException("Type not found or could not be converted.");

        /* Undefine current value */
        undefine(key);

        final String jsonKey = JsonBasics.getTypeInfoAndKey(type, key);
        this.data.add(jsonKey, element);
        this.keysToTypeAndKeys.put(key, new TypeAndKey(type, key));
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this.data);
    }
}
