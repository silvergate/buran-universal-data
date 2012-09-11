package com.gmail.at.silvergate.buran.universalData.jsonImpl;

import com.gmail.at.silvergate.buran.universalData.IType;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */
public class TypeAndKey {
    private final IType<?> type;
    private final String key;

    public TypeAndKey(IType<?> type, String key) {
        this.type = type;
        this.key = key;
    }

    public IType<?> getType() {
        return type;
    }

    public String getKey() {
        return key;
    }
}
