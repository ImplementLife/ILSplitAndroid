package com.impllife.split.data.constant;

import com.impllife.split.R;

public enum DefaultUserIcon implements ImgDbMapping {
    ic_png_default_user_1(R.drawable.ic_png_default_user_1, "default_user_1"),
    ;
    public final int id;
    public final String name;

    DefaultUserIcon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DefaultUserIcon parse(String name) {
        for (DefaultUserIcon value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getResId() {
        return id;
    }
}
