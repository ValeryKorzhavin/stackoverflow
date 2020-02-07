package ru.valerykorzh.springdemo.service.converter;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.TimeZone;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
//        return localDateTime.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
//        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
//                        TimeZone.getDefault().toZoneId());
    }
//    @Override
//    public ZonedDateTime convertToDatabaseColumn(LocalDateTime localDateTime) {
//        if (localDateTime == null) return null;
//        TimeZone timeZone = LocaleContextHolder.getTimeZone();
//        ZoneId zoneId = ZoneId.of(timeZone.getID());
//        return ZonedDateTime.of(localDateTime, zoneId);
//    }
//
//    @Override
//    public LocalDateTime convertToEntityAttribute(ZonedDateTime zonedDateTime) {
//        if (zonedDateTime == null) return null;
//        ZoneId zoneId = zonedDateTime.getZone();
//        Instant instant = zonedDateTime.toInstant();
//        return LocalDateTime.ofInstant(instant, zoneId);
//    }

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
