package com.work.finalproject.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Data
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = true)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "moddate", updatable = true)
    private LocalDateTime modDate;
}
