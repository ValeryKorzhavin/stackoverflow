package ru.valerykorzh.springdemo.service.converter;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.*;
import java.util.Locale;
import java.util.TimeZone;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, ZonedDateTime> {
    @Override
    public ZonedDateTime convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        TimeZone timeZone = LocaleContextHolder.getTimeZone();
        ZoneId zoneId = ZoneId.of(timeZone.getID());
        return ZonedDateTime.of(localDateTime, zoneId);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) return null;
        ZoneId zoneId = zonedDateTime.getZone();
        Instant instant = zonedDateTime.toInstant();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

//    @Override
//    public OffsetDateTime convertToDatabaseColumn(LocalDateTime localDateTime) {
//        if (localDateTime == null) return null;
//        TimeZone timeZone = LocaleContextHolder.getTimeZone();
//        ZoneId zoneId = ZoneId.of(timeZone.getID());
//        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
//        return OffsetDateTime.of(localDateTime, zoneOffset);
//    }
//
//    @Override
//    public LocalDateTime convertToEntityAttribute(OffsetDateTime offsetDateTime) {
//        if (offsetDateTime == null) return null;
//        ZoneOffset zoneOffset = offsetDateTime.getOffset();
//        ZoneId zoneId = ZoneId.ofOffset("UTC", zoneOffset);
//        Instant instant = offsetDateTime.toInstant();
//        return LocalDateTime.ofInstant(instant, zoneId);
//    }
}
