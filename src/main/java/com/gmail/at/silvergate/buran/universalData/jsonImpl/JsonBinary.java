package com.gmail.at.silvergate.buran.universalData.jsonImpl;

import com.gmail.at.silvergate.buran.universalData.IBinaryReader;
import com.gmail.at.silvergate.buran.universalData.IBinaryType;
import com.gmail.at.silvergate.buran.universalData.IBinaryWriter;
import com.google.gson.JsonPrimitive;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 11.09.12
 * Time: 22:50
 * To change this template use File | Settings | File Templates.
 */
public class JsonBinary implements IBinaryType {

    private String base64String = "";

    public JsonBinary() {
    }

    public JsonBinary(JsonPrimitive inputData) {
        this();
        this.base64String = inputData.getAsString();
    }

    public JsonPrimitive getJsonPrimitive() {
        return new JsonPrimitive(this.base64String);
    }

    @Override
    public void read(IBinaryReader reader) {
        if (this.base64String.length() > 0) {
            final String inputString = this.base64String;
            final byte[] binary = Base64.decodeBase64(inputString);
            final ByteArrayInputStream bais = new ByteArrayInputStream(binary);
            reader.read(bais);
        } else {
            final ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
            reader.read(bais);
        }
    }

    @Override
    public void write(IBinaryWriter writer) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.write(baos);
        try {
            baos.close();
            final String finalString = Base64.encodeBase64String(baos.toByteArray());
            this.base64String = finalString;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
