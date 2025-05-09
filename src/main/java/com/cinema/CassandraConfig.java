package com.cinema;

import com.cinema.dao.model.AbstractEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.mapping.event.BeforeConvertCallback;

import java.util.Date;
import java.util.UUID;

@Configuration
public class CassandraConfig {

    @Bean
    public BeforeConvertCallback<AbstractEntity> beforeConvertCallback() {
        return (entity, tableName) -> {
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
                entity.setCreationTimestamp(new Date());
            }
            entity.setModificationTimestamp(new Date());
            return entity;
        };
    }
}
