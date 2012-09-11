package com.gmail.at.silvergate.buran.universalData.memoryImpl;

import com.gmail.at.silvergate.buran.universalData.IArray;
import com.gmail.at.silvergate.buran.universalData.IType;
import com.gmail.at.silvergate.buran.universalData.Types;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class MemArray implements IArray {

    private final List<Object> data = new ArrayList<Object>();

    @Override
    public int getSize() {
        return this.data.size();
    }

    @Override
    public <T> Optional<T> as(int index, IType<T> type) {
        if (index >= getSize())
            return Optional.absent();
        final Object value = this.data.get(index);
        if (value == null)
            return Optional.absent();
        final Class<?> isClass = value.getClass();
        if (!type.getJavaType().isAssignableFrom(isClass))
            return Optional.absent();
        return Optional.of((T) value);
    }

    @Override
    public <T> void set(int index, IType<T> type, T value) {
        this.data.set(index, value);
    }

    @Override
    public <T> void add(IType<T> type, T value) {
        this.data.add(value);
    }

    @Override
    public boolean remove(int index) {
        return this.data.remove(index) != null;
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public <T> Optional<IType<T>> getType(int index) {
        if (index >= getSize())
            return Optional.absent();
        final Object value = this.data.get(index);
        if (value == null)
            return Optional.absent();
        for (IType<?> type : Types.TYPES) {
            if (type.getJavaType().isAssignableFrom(value.getClass())) {
                final IType<T> castType = (IType<T>) type;
                return Optional.of(castType);
            }

        }
        return Optional.absent();
    }
}
