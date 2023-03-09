package com.impllife.split.data.jpa.entity;

import com.impllife.split.data.Sync;
import com.impllife.split.data.jpa.provide.WithId;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

public class EntityWithId implements WithId<Long>, Sync<Long> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long serverId = -1L;

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getServerId() {
        return serverId;
    }
    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
    @Override
    public boolean isSync() {
        return serverId >= 0;
    }
}
