package com.impllife.split.data;

public interface Sync<K> {
    K getServerId();
    boolean isSync();
}
