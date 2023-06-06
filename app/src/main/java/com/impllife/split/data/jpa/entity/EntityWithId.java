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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityWithId that = (EntityWithId) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getServerId() != null ? getServerId().equals(that.getServerId()) : that.getServerId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getServerId() != null ? getServerId().hashCode() : 0);
        return result;
    }
}
