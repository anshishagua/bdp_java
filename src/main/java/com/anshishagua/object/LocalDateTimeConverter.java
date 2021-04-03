package com.anshishagua.object;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTimeConverter.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

public class LocalDateTimeConverter extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS")));
    }
}
