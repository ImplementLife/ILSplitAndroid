package com.impllife.split.data.jpa.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import com.impllife.split.data.Sync;
import com.impllife.split.data.jpa.provide.WithId;

public class EntityWithId implements WithId<Integer>, Sync<Integer> {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(defaultValue = "-1")
    private Integer serverId;

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getServerId() {
        return serverId;
    }
    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }
    @Override
    public boolean isSync() {
        return serverId >= 0;
    }
}
