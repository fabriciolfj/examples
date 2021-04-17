package br.com.coffeeandit.app.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public interface BuilderObject<T extends BuilderObject> {


    Integer getId();

    String getName();

   default String signature() {
        if (isValid()) {
            return BuilderObject.encode((getId() + ":" + getName()));
        }
        return "";
    }

    abstract T name(final String name);

    abstract T id(final Integer id);

    static Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    private static String encode(final String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(getCharset()));

    }

    private boolean isValid() {
        return Objects.nonNull(getId()) && Objects.nonNull(getName());
    }

    T build();


}
