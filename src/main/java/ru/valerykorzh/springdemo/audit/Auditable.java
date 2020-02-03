package ru.valerykorzh.springdemo.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;

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
//    @Convert(converter = LocalDateTimeConverter.class)
//    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    protected OffsetDateTime createdDate;

    @ManyToOne
    @LastModifiedBy
    @JoinColumn(name = "last_modified_by")
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
//    @Convert(converter = LocalDateTimeConverter.class)
//    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    protected OffsetDateTime lastModifiedDate;

}
