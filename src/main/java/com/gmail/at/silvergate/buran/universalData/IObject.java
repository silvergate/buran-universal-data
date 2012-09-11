package com.gmail.at.silvergate.buran.universalData;

import com.google.common.base.Optional;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public interface IObject {
    boolean isDefined(String key);

    boolean undefine(String key);

    Set<String> getDefinedKeys();

    Optional<IType<?>> getType(String key);

    <T> Optional<T> as(String key, IType<T> type);

    <T> void set(String key, IType<T> type, T value);
}
