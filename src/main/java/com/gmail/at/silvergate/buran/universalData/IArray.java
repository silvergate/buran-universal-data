package com.gmail.at.silvergate.buran.universalData;

import com.google.common.base.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public interface IArray {
    int getSize();

    <T> Optional<T> as(int index, IType<T> type);

    <T> void set(int index, IType<T> type, T value);

    <T> void add(IType<T> type, T value);

    boolean remove(int index);

    void clear();

    <T> Optional<IType<T>> getType(int index);
}
