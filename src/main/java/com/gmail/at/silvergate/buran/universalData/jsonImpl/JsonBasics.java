package com.gmail.at.silvergate.buran.universalData.jsonImpl;

import com.gmail.at.silvergate.buran.universalData.*;
import com.google.common.base.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 23:30
 * To change this template use File | Settings | File Templates.
 */
public class JsonBasics {

    private static Map<Character, IType<?>> prefixToTypeMap = new HashMap<Character, IType<?>>();
    private static Map<IType<?>, Character> typeToPrefixMap = new HashMap<IType<?>, Character>();

    private static void add(Character prefix, IType<?> type) {
        prefixToTypeMap.put(prefix, type);
        typeToPrefixMap.put(type, prefix);
    }

    static {
        add('I', Types.INT);
        add('S', Types.STRING);
        add('L', Types.BOOLEAN);
        add('O', Types.OBJECT);
        add('A', Types.ARRAY);
        add('B', Types.BINARY);
    }

    public static TypeAndKey fromJsonKeyToTypeAndKey(final String jsonKey) {
        final String key = removeTypeInformationFromKey(jsonKey);
        final char typePrefix = jsonKey.charAt(0);
        final IType<?> type = prefixToTypeMap.get(typePrefix);
        return new TypeAndKey(type, key);
    }

    public static Character getTypeCharacterFromType(final IType<?> type) {
        return typeToPrefixMap.get(type);
    }

    public static IType<?> getTypeFromPrefix(final char character) {
        return prefixToTypeMap.get(character);
    }

    public static String removeTypeInformationFromKey(String typeInfoAndKey) {
        return typeInfoAndKey.substring(1);
    }

    public static String getTypeInfoAndKey(IType<?> type, String key) {
        return MessageFormat.format("{0}{1}", typeToPrefixMap.get(type), key);
    }

    public static <T> T fromPrimitive(JsonElement primitive, IType<T> type) {
        if (type.equals(Types.INT)) {
            return (T) (new Integer(primitive.getAsInt()));
        }

        if (type.equals(Types.STRING)) {
            return (T) (primitive.getAsString());
        }

        if (type.equals(Types.BOOLEAN)) {
            return (T) (new Boolean(primitive.getAsBoolean()));
        }

        if (type.equals(Types.OBJECT)) {
            return (T) (new JsonObject(primitive.getAsJsonObject()));
        }

        if (type.equals(Types.ARRAY)) {
            return (T) (new JsonArray(primitive.getAsJsonArray()));
        }

        if (type.equals(Types.BINARY)) {
            return (T) (new JsonBinary(primitive.getAsJsonPrimitive()));
        }

        return null;
    }

    public static <T> JsonElement fromJavaType(T value, IType<T> type) {
        if (type.equals(Types.INT)) {
            return new JsonPrimitive((Integer) value);
        }

        if (type.equals(Types.STRING)) {
            return new JsonPrimitive((String) value);
        }

        if (type.equals(Types.BOOLEAN)) {
            return new JsonPrimitive((Boolean) value);
        }

        if (type.equals(Types.OBJECT)) {
            final com.google.gson.JsonObject targetBacking = new com.google.gson.JsonObject();
            final IObject target = new JsonObject(targetBacking);
            final IObject src = (IObject) value;
            for (final String key : src.getDefinedKeys()) {
                final Optional<IType<?>> srcType = src.getType(key);
                final Optional<?> valueOpt = src.as(key, srcType.get());
                target.set(key, (IType<Object>) srcType.get(), (Object) valueOpt.get());
            }
            return targetBacking;
        }

        if (type.equals(Types.ARRAY)) {
            final IArray src = (IArray) value;
            final JsonArray target = new JsonArray();
            for (int index = 0; index < src.getSize(); index++) {
                final Optional<IType<Object>> srcTypeOpt = src.getType(index);
                final IType<?> srcType = srcTypeOpt.get();
                final Optional<?> srcValueOpt = src.as(index, srcType);
                final Object srcValue = srcValueOpt.get();
                target.add((IType<Object>) srcType, srcValue);
            }
            return target.getBackingArray();
        }

        if (type.equals(Types.BINARY)) {
            final IBinaryType src = (IBinaryType) value;
            final JsonBinary target = new JsonBinary();
            src.read(new IBinaryReader() {
                @Override
                public void read(final InputStream reader) {
                    target.write(new IBinaryWriter() {
                        @Override
                        public void write(final OutputStream writer) {
                            try {
                                IOUtils.copy(reader, writer);
                            } catch (IOException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }
                    });
                }
            });
            return target.getJsonPrimitive();
        }

        return null;
    }

}
