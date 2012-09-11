package com.gmail.at.silvergate.buran.universalData;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 22:40
 * To change this template use File | Settings | File Templates.
 */
public interface IBinaryType {
    void read(IBinaryReader reader);

    void write(IBinaryWriter writer);
}
