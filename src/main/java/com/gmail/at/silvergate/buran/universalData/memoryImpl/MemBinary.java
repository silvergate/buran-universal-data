package com.gmail.at.silvergate.buran.universalData.memoryImpl;

import com.gmail.at.silvergate.buran.universalData.IBinaryReader;
import com.gmail.at.silvergate.buran.universalData.IBinaryType;
import com.gmail.at.silvergate.buran.universalData.IBinaryWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
public class MemBinary implements
        IBinaryType {

    private byte[] data = new byte[0];

    @Override
    public void read(IBinaryReader reader) {
        final ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
        reader.read(bais);
    }

    @Override
    public void write(IBinaryWriter writer) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.write(baos);
        try {
            baos.close();
            this.data = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
