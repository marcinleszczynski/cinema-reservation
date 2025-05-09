package com.cinema.dao.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.Date;
import java.util.UUID;

@Data
public abstract class AbstractEntity {


    @Column("creation_timestamp")
    private Date creationTimestamp;

    @Column("modification_timestamp")
    private Date modificationTimestamp;

    public abstract UUID getId();
    public abstract void setId(UUID id);
}
