package com.tsy.blog.admin.web.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author Steven.T
 * @date 2022/1/26
 */

@Component
public class DateTimeFormatSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Instant instant = Instant.ofEpochMilli(value);
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        final String format = formatter.withLocale(Locale.CHINA)
                .format(ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai")));
        gen.writeString(format);
    }
}
