package com.gmail.at.silvergate.buran.universalData.memoryImpl;

import com.gmail.at.silvergate.buran.universalData.IObject;
import com.gmail.at.silvergate.buran.universalData.IType;
import com.gmail.at.silvergate.buran.universalData.Types;
import com.google.common.base.Optional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class MemObject implements IObject {

    private final Map<String, Object> data = new HashMap<String, Object>();

    @Override
    public boolean isDefined(String key) {
        return this.data.containsKey(key);
    }

    @Override
    public boolean undefine(String key) {
        return this.data.remove(key) != null;
    }

    @Override
    public Set<String> getDefinedKeys() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    @Override
    public Optional<IType<?>> getType(String key) {
        final Object value = this.data.get(key);
        if (value == null)
            return Optional.absent();
        for (IType<?> type : Types.TYPES) {
            if (type.getJavaType().isAssignableFrom(value.getClass())) {
                return (Optional<IType<?>>) Optional.of(((IType<?>) type));
            }
        }
        return Optional.absent();
    }

    @Override
    public <T> Optional<T> as(String key, IType<T> type) {
        /* Type ok? */
        Optional<IType<?>> realType = getType(key);
        if (!realType.isPresent())
            return Optional.absent();

        if (!realType.get().equals(type))
            return Optional.absent();

        final Object value = this.data.get(key);
        return Optional.of((T) value);
    }

    @Override
    public <T> void set(String key, IType<T> type, T value) {
        assert (value != null);

        if (!type.getJavaType().isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("Value is of wrong type");

        this.data.put(key, (T) value);
    }
}
