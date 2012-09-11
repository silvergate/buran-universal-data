package com.gmail.at.silvergate.buran.universalData.tests;

import com.gmail.at.silvergate.buran.universalData.*;
import com.gmail.at.silvergate.buran.universalData.jsonImpl.JsonArray;
import com.gmail.at.silvergate.buran.universalData.jsonImpl.JsonBinary;
import com.gmail.at.silvergate.buran.universalData.jsonImpl.JsonObject;
import com.gmail.at.silvergate.buran.universalData.memoryImpl.MemObject;
import com.google.common.base.Optional;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 00:31
 * To change this template use File | Settings | File Templates.
 */
public class Tests {
    public static void main(String[] args) throws UnsupportedEncodingException {
        IObject memObj = new MemObject();
        memObj.set("key1", Types.INT, 33);
        System.out.println("key1=" + memObj.as("key1", Types.INT));

        JsonObject jsonObject = new JsonObject();
        jsonObject.set("welt", Types.INT, 44);
        System.out.println("key1(json)=" + jsonObject.as("welt", Types.INT));

        JsonObject innerObj = new JsonObject();
        innerObj.set("innerKey", Types.INT, 32);

        JsonArray jsa = new JsonArray();
        jsa.add(Types.STRING, "Ich bin ein String im Array");
        jsa.add(Types.INT, 666);
        jsa.add(Types.OBJECT, innerObj);
        jsonObject.set("keyZumArray", Types.ARRAY, jsa);

        final byte[] binarySrc = (new String("Ich bin binäre daten, encodiert in UTF-8")).getBytes("UTF-8");
        final IBinaryType binary = new JsonBinary();
        binary.write(new IBinaryWriter() {
            @Override
            public void write(OutputStream writer) {
                try {
                    writer.write(binarySrc);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        jsonObject.set("binaryKey", Types.BINARY, binary);

        System.out.println("json=" + jsonObject.toJsonString());

        final Optional<IBinaryType> binOutput = jsonObject.as("binaryKey", Types.BINARY);
        binOutput.get().read(new IBinaryReader() {
            @Override
            public void read(InputStream reader) {
            }
        });


    }
}
