package com.gmail.at.silvergate.buran.universalData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: caelis
 * Date: 10.09.12
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public class Types {

    private static class Type<T> implements IType<T> {
        private final Class<T> type;

        private Type(Class<T> type) {
            this.type = type;
        }

        @Override
        public Class<T> getJavaType() {
            return this.type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Type type1 = (Type) o;

            if (type != null ? !type.equals(type1.type) : type1.type != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return type != null ? type.hashCode() : 0;
        }
    }

    public static final IType<Integer> INT = new Type<>(Integer.class);
    public static final IType<String> STRING = new Type<>(String.class);
    public static final IType<Boolean> BOOLEAN = new Type<>(Boolean.class);
    public static final IType<IObject> OBJECT = new Type<>(IObject.class);
    public static final IType<IArray> ARRAY = new Type<>(IArray.class);
    public static final IType<IBinaryType> BINARY = new Type<>(IBinaryType.class);


    public static final Set<IType<?>> TYPES;

    static {
        final Set<IType<?>> types = new HashSet<IType<?>>();
        types.add(INT);
        types.add(STRING);
        types.add(BOOLEAN);
        types.add(OBJECT);
        types.add(ARRAY);
        types.add(BINARY);

        TYPES = Collections.synchronizedSet(Collections.unmodifiableSet(types));
    }


}
