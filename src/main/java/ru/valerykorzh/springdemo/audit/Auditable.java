package ru.valerykorzh.springdemo.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.valerykorzh.springdemo.service.converter.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "created_by")
    protected U createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    protected LocalDateTime createdDate;

    @ManyToOne
    @LastModifiedBy
    @JoinColumn(name = "last_modified_by")
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @Convert(converter = LocalDateTimeConverter.class)
    protected LocalDateTime lastModifiedDate;

    public String getFormattedDate(LocalDateTime timestamp, String pattern) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return timestamp.format(formatter);
    }

}
