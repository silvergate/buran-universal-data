package com.gmail.at.silvergate.buran.universalData;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public interface IType<T> {
    Class<T> getJavaType();
}
