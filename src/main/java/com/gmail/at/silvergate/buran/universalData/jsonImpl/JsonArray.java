package com.gmail.at.silvergate.buran.universalData.jsonImpl;

import com.gmail.at.silvergate.buran.universalData.IArray;
import com.gmail.at.silvergate.buran.universalData.IType;
import com.google.common.base.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class JsonArray implements IArray {

    private List<JsonElement> data;
    private List<Character> types;

    public JsonArray() {
        this.data = new ArrayList<JsonElement>();
        this.types = new ArrayList<Character>();
    }

    public JsonArray(final com.google.gson.JsonArray copyFrom) {
        this();

        /* Extract types */
        if (copyFrom.size() > 0)
            for (JsonElement element : copyFrom.get(0).getAsJsonArray())
                this.types.add(element.getAsString().charAt(0));

        /* Extract elements */
        if (copyFrom.size() > 1) {
            for (int index = 1; index < copyFrom.size(); index++) {
                this.data.add(copyFrom.get(index));
            }
        }
    }

    public com.google.gson.JsonArray getBackingArray() {
        /* Generate types */
        final com.google.gson.JsonArray typesArray = new com.google.gson.JsonArray();
        for (final Character type : this.types)
            typesArray.add(new JsonPrimitive(type));

        /* Add elements */
        final com.google.gson.JsonArray dataArray = new com.google.gson.JsonArray();
        dataArray.add(typesArray);
        for (final JsonElement element : this.data) {
            dataArray.add(element);
        }

        return dataArray;
    }

    private com.google.gson.JsonArray getTypeArray() {
        return this.data.get(0).getAsJsonArray();
    }

    @Override
    public int getSize() {
        return this.data.size();
    }

    @Override
    public <T> Optional<T> as(int index, IType<T> type) {
        if (index >= this.data.size())
            return Optional.absent();

        final Character typeChar = this.types.get(index);
        final JsonElement element = this.data.get(index);
        final IType<?> typeIs = JsonBasics.getTypeFromPrefix(typeChar);

        if (!type.getJavaType().isAssignableFrom(typeIs.getJavaType()))
            return Optional.absent();

        final T javaValue = (T) JsonBasics.fromPrimitive(element, typeIs);
        return Optional.of(javaValue);
    }

    @Override
    public <T> void set(int index, IType<T> type, T value) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public <T> void add(IType<T> type, T value) {
        this.data.add(JsonBasics.fromJavaType(value, type));
        this.types.add(JsonBasics.getTypeCharacterFromType(type));
    }

    @Override
    public boolean remove(int index) {
        if (index >= this.data.size())
            return false;
        this.data.remove(index);
        this.types.remove(index);
        return true;
    }

    @Override
    public void clear() {
        this.data.clear();
        this.types.clear();
    }

    @Override
    public <T> Optional<IType<T>> getType(int index) {
        if (index >= this.data.size())
            return Optional.absent();
        final Character typeChar = this.types.get(index);
        final IType<?> typeIs = JsonBasics.getTypeFromPrefix(typeChar);
        return Optional.of((IType<T>) typeIs);
    }
}
